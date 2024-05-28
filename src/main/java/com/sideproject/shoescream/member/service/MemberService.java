package com.sideproject.shoescream.member.service;

import com.sideproject.shoescream.bid.constant.BidStatus;
import com.sideproject.shoescream.bid.constant.BidType;
import com.sideproject.shoescream.bid.constant.DealStatus;
import com.sideproject.shoescream.bid.entity.Bid;
import com.sideproject.shoescream.bid.entity.Deal;
import com.sideproject.shoescream.bid.repository.BidRepository;
import com.sideproject.shoescream.bid.repository.DealRepository;
import com.sideproject.shoescream.global.exception.ErrorCode;

import com.sideproject.shoescream.member.dto.request.KaKaoSignInRequest;
import com.sideproject.shoescream.member.dto.request.MemberFindMemberInfoRequest;
import com.sideproject.shoescream.member.dto.request.MemberSignInRequest;
import com.sideproject.shoescream.member.dto.request.MemberSignUpRequest;
import com.sideproject.shoescream.member.dto.response.MemberResponse;
import com.sideproject.shoescream.member.dto.response.MemberSignInResponse;
import com.sideproject.shoescream.member.dto.response.MyBuyingHistoryResponse;
import com.sideproject.shoescream.member.dto.response.MySellingHistoryResponse;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.member.exception.*;
import com.sideproject.shoescream.member.repository.MemberRepository;
import com.sideproject.shoescream.member.util.JwtTokenUtil;
import com.sideproject.shoescream.member.util.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final BidRepository bidRepository;
    private final DealRepository dealRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return memberRepository.findByMemberId(userId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public MemberResponse signUp(MemberSignUpRequest memberSignUpRequest) {
        checkMemberId(memberSignUpRequest.memberId());
        checkPassword(memberSignUpRequest.password());
        checkName(memberSignUpRequest.name());
        // 이메일 인증 여부 예외 처리

        return MemberMapper.toMemberResponse(memberRepository.save(
                MemberMapper.toMember(memberSignUpRequest,
                        encoder.encode(memberSignUpRequest.password()))));
    }

    public MemberSignInResponse signIn(MemberSignInRequest memberSignInRequest) {
        Member member = memberRepository.findByMemberId(memberSignInRequest.memberId())
                .orElseThrow(
                        () -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        if (!encoder.matches(memberSignInRequest.password(), member.getPassword())) {
            throw new InvalidMemberIdAndPasswordException(ErrorCode.INVALID_USER_ID_AND_PASSWORD);
        }

        String accessToken = jwtTokenUtil.generateToken(member.getMemberId());
        String refreshToken = jwtTokenUtil.generateRefreshToken(member.getMemberId());

        return MemberMapper.toSignInResponse(MemberMapper.toMemberResponse(member),
                MemberMapper.toTokenResponse(accessToken, refreshToken));
    }

    public MemberSignInResponse kakaoLogin(KaKaoSignInRequest kaKaoSignInRequest) {
        Member member = memberRepository.findByEmail(kaKaoSignInRequest.email()).orElse(null);
        if (member == null) {
            member = Member.builder()
                    .memberNumber(kaKaoSignInRequest.id())
                    .memberId("kakao" + kaKaoSignInRequest.id())
                    .email(kaKaoSignInRequest.email())
                    .name(kaKaoSignInRequest.nickname())
                    .profileImage(kaKaoSignInRequest.profile_image())
                    .build();
            memberRepository.save(member);
        }
        String accessToken = jwtTokenUtil.generateToken(member.getMemberId());
        String refreshToken = jwtTokenUtil.generateRefreshToken(member.getMemberId());

        return MemberMapper.toSignInResponse(MemberMapper.toMemberResponse(member),
                MemberMapper.toTokenResponse(accessToken, refreshToken));
    }

    public String findMemberId(String mail) {
        Member member = memberRepository.findByEmail(mail)
                .orElseThrow(
                        () -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        return member.getMemberId();
    }

    private void checkMemberId(String memberId) {
        if (memberRepository.existsByMemberId(memberId)) {
            throw new AlreadyExistMemberIdException(ErrorCode.ALREADY_EXIST_USER_ID);
        }
    }

    private void checkPassword(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@#$&*])(?=\\S+$).{9,}$";
        if (!password.matches(passwordPattern)) {
            throw new InvalidPasswordException(ErrorCode.INVALID_PASSWORD);
        }
    }

    private void checkName(String name) {
        String namePattern = "[\\p{L}\\d]{1,10}";
        if (!name.matches(namePattern)) {
            throw new InvalidMemberNameException(ErrorCode.INVALID_MEMBER_NAME);
        }
    }

    public List<MyBuyingHistoryResponse> getMyBuyingHistory(String status, LocalDate startDate, LocalDate endDate, Authentication authentication) {
        Member member = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() -> new RuntimeException());

        if (status.equals("bidding")) {
            List<Bid> buyingBids = filterMyBuyingBiddingByDateRange(startDate, endDate, member);
            return buyingBids.stream()
                    .map(MemberMapper::toMyBuyingBidHistoryResponse)
                    .collect(Collectors.toList());
        } else if (status.equals("pending")) {
            List<Deal> pendingDeals = filterMyBuyingPendingByDateRange(startDate, endDate, member);
            return pendingDeals.stream()
                    .map(MemberMapper::toMyBuyingPendingDealHistoryResponse)
                    .collect(Collectors.toList());
        }

        return filterMyBuyingFinishedByDateRange(startDate, endDate, member).stream()
                .map(MemberMapper::toMyBuyingFinishedDealHistoryResponse)
                .collect(Collectors.toList());
    }

    public List<MySellingHistoryResponse> getMySellingHistory(String status, LocalDate startDate, LocalDate endDate, Authentication authentication) {
        Member member = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() -> new RuntimeException());

        if (status.equals("bidding")) {
            List<Bid> sellingBids = filterMySellingBiddingByDateRange(startDate, endDate, member);
            return sellingBids.stream()
                    .map(MemberMapper::toMySellingBidHistoryResponse)
                    .collect(Collectors.toList());
        } else if (status.equals("pending")) {
            List<Deal> pendingDeals = filterMySellingPendingByDateRange(startDate, endDate, member);
            return pendingDeals.stream()
                    .map(MemberMapper::toMySellingPendingDealHistoryResponse)
                    .collect(Collectors.toList());
        }
        return filterMySellingFinishedByDateRange(startDate, endDate, member).stream()
                .map(MemberMapper::toMySellingFinishedDealHistoryResponse)
                .collect(Collectors.toList());
    }

    private List<Bid> filterMyBuyingBiddingByDateRange(LocalDate startDate, LocalDate endDate, Member member) {
        List<Bid> myBiddingHistory = bidRepository.findByMemberNumber(member.getMemberNumber());
        if (startDate == null & endDate == null) {
            return myBiddingHistory.stream()
                    .filter(bid -> bid.getBidType().getBidType().equals(BidType.BUY_BID.getBidType()))
                    .filter(bid -> bid.getBidStatus().getBidStatus().equals(BidStatus.WAITING_MATCHING.getBidStatus()))
                    .toList();
        }

        return myBiddingHistory.stream()
                .filter(bid -> bid.getBidType().getBidType().equals(BidType.BUY_BID.getBidType()))
                .filter(bid -> bid.getBidStatus().getBidStatus().equals(BidStatus.WAITING_MATCHING.getBidStatus()))
                .filter(bid -> bid.getCreatedAt().toLocalDate().isAfter(startDate) &&
                        bid.getCreatedAt().toLocalDate().isBefore(endDate))
                .toList();
    }


    private List<Deal> filterMyBuyingPendingByDateRange(LocalDate startDate, LocalDate endDate, Member member) {
        List<Deal> myPendingHistory = dealRepository.findByMemberNumber(member.getMemberNumber());
        if (startDate == null && endDate == null) {
            return myPendingHistory.stream()
                    .filter(deal -> deal.getDealStatus().getDealStatus().equals(DealStatus.WAITING_DEPOSIT.getDealStatus()))
                    .filter(deal -> deal.getDealStatus().getDealStatus().equals(DealStatus.COMPLETE_DEPOSIT.getDealStatus()))
                    .toList();
        }

        return myPendingHistory.stream()
                .filter(deal -> deal.getDealStatus().getDealStatus().equals(DealStatus.WAITING_TRANSFER.getDealStatus()))
                .filter(deal -> deal.getDealStatus().getDealStatus().equals(DealStatus.COMPLETE_TRANSFER.getDealStatus()))
                .filter(deal -> deal.getCreatedAt().toLocalDate().isAfter(startDate) &&
                        deal.getCreatedAt().toLocalDate().isBefore(endDate))
                .toList();
    }

    private List<Deal> filterMyBuyingFinishedByDateRange(LocalDate startDate, LocalDate endDate, Member member) {
        List<Deal> myFinishedHistory = dealRepository.findByMemberNumber(member.getMemberNumber());
        if (startDate == null && endDate == null) {
            return myFinishedHistory.stream()
                    .filter(deal -> deal.getDealStatus().getDealStatus().equals(DealStatus.SUCCESS_DEAL.getDealStatus()))
                    .filter(deal -> deal.getDealStatus().getDealStatus().equals(DealStatus.FAIL_DEAL.getDealStatus()))
                    .toList();
        }

        return myFinishedHistory.stream()
                .filter(deal -> deal.getDealStatus().getDealStatus().equals(DealStatus.SUCCESS_DEAL.getDealStatus()))
                .filter(deal -> deal.getDealStatus().getDealStatus().equals(DealStatus.FAIL_DEAL.getDealStatus()))
                .filter(deal -> deal.getTradedAt().toLocalDate().isAfter(startDate) &&
                        deal.getTradedAt().toLocalDate().isBefore(endDate))
                .toList();
    }

    private List<Bid> filterMySellingBiddingByDateRange(LocalDate startDate, LocalDate endDate, Member member) {
        List<Bid> myBiddingHistory = bidRepository.findByMemberNumber(member.getMemberNumber());
        if (startDate == null & endDate == null) {
            return myBiddingHistory.stream()
                    .filter(bid -> bid.getBidType().getBidType().equals(BidType.SELL_BID.getBidType()))
                    .filter(bid -> bid.getBidStatus().getBidStatus().equals(BidStatus.WAITING_MATCHING.getBidStatus()))
                    .toList();
        }

        return myBiddingHistory.stream()
                .filter(bid -> bid.getBidType().getBidType().equals(BidType.SELL_BID.getBidType()))
                .filter(bid -> bid.getBidStatus().getBidStatus().equals(BidStatus.WAITING_MATCHING.getBidStatus()))
                .filter(bid -> bid.getCreatedAt().toLocalDate().isAfter(startDate) &&
                        bid.getCreatedAt().toLocalDate().isBefore(endDate))
                .toList();
    }

    private List<Deal> filterMySellingPendingByDateRange(LocalDate startDate, LocalDate endDate, Member member) {
        List<Deal> myPendingHistory = dealRepository.findByMemberNumber(member.getMemberNumber());
        if (startDate == null && endDate == null) {
            return myPendingHistory.stream()
                    .filter(deal -> deal.getDealStatus().getDealStatus().equals(DealStatus.WAITING_DEPOSIT.getDealStatus()))
                    .filter(deal -> deal.getDealStatus().getDealStatus().equals(DealStatus.COMPLETE_DEPOSIT.getDealStatus()))
                    .toList();
        }

        return myPendingHistory.stream()
                .filter(deal -> deal.getDealStatus().getDealStatus().equals(DealStatus.WAITING_DEPOSIT.getDealStatus()))
                .filter(deal -> deal.getDealStatus().getDealStatus().equals(DealStatus.COMPLETE_DEPOSIT.getDealStatus()))
                .filter(deal -> deal.getCreatedAt().toLocalDate().isAfter(startDate) &&
                        deal.getCreatedAt().toLocalDate().isBefore(endDate))
                .toList();
    }

    private List<Deal> filterMySellingFinishedByDateRange(LocalDate startDate, LocalDate endDate, Member member) {
        List<Deal> myFinishedHistory = dealRepository.findByMemberNumber(member.getMemberNumber());
        if (startDate == null && endDate == null) {
            return myFinishedHistory.stream()
                    .filter(deal -> deal.getDealStatus().getDealStatus().equals(DealStatus.SUCCESS_DEAL.getDealStatus()))
                    .filter(deal -> deal.getDealStatus().getDealStatus().equals(DealStatus.FAIL_DEAL.getDealStatus()))
                    .toList();
        }

        return myFinishedHistory.stream()
                .filter(deal -> deal.getDealStatus().getDealStatus().equals(DealStatus.SUCCESS_DEAL.getDealStatus()))
                .filter(deal -> deal.getDealStatus().getDealStatus().equals(DealStatus.FAIL_DEAL.getDealStatus()))
                .filter(deal -> deal.getTradedAt().toLocalDate().isAfter(startDate) &&
                        deal.getTradedAt().toLocalDate().isBefore(endDate))
                .toList();
    }
}
