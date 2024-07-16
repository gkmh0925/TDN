package oms.kisvan.emart.model.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PagingDTO {
	private Integer startPage; // 시작페이지
	private Integer endPage; // 종료페이지
	private boolean prev, next; // 이전,다음여부

	private Integer total; // 전체 건수

	private Integer totalPage; // Total 마지막 페이지

	private Criteria cri; // 페이징

	public PagingDTO(Criteria cri, int total) {
		this.total = total;
		this.cri = cri;

		// 페이징 신식처리
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;// 한 페이지당 100개씩 10페이지

		this.startPage = this.endPage - 9;

		// 실제 마지막 페이지
		int realEnd = (int) (Math.ceil(total * 1.0 / cri.getAmount()));

		// Total 마지막 페이지
		this.totalPage = total / cri.getAmount();
		if (total % cri.getAmount() > 0) {
			totalPage++;
		}

		// 마지막 페이지 보정
		if (realEnd <= this.endPage)
			this.endPage = realEnd;

		// 이전 페이지 다음 페이지 존재 여부
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;

	}

}
