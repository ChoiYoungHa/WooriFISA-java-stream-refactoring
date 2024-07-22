/** 
 * PROJECT  : 재능기부 프로젝트
 * NAME  :  TalentDonationProjectService.java
 * DESC  :  재능 기부 프로젝트를 저장, 수정, 삭제, 검색하는 서비스 로직
 * 
 * @author  
 * @version 1.0
*/

package probono.service;

import java.util.ArrayList;
import java.util.Optional;

import probono.model.dto.Beneficiary;
import probono.model.dto.Donator;
import probono.model.dto.TalentDonationProject;


public class TalentDonationProjectService {

	// singleton design pattern
	private static TalentDonationProjectService instance = new TalentDonationProjectService();

	/**   
	 * 진행중인 Project를 저장
	 */
	private ArrayList<TalentDonationProject> donationProjectList = new ArrayList<TalentDonationProject>();

	private TalentDonationProjectService() {}

	public static TalentDonationProjectService getInstance() {
		return instance;
	}

	/**
	 * 모든 Project 검색
	 * 
	 * @return 모든 Project
	 */
	public ArrayList<TalentDonationProject> getDonationProjectsList() {
		return donationProjectList;
	}

	// TO DO - 구현 및 완성해야 할 job
	/**
	 * Project 이름으로 검색 - 객체된 Project 반환하기
	 * 
	 * @param projectName 프로젝트 이름
	 * @return TalentDonationProject 검색된 프로젝트
	 */
	public TalentDonationProject getDonationProject(String projectName) {
		
//		for(TalentDonationProject tp : donationProjectList) {
//			if(tp.getTalentDonationProjectName().equals(projectName)) {
//				return tp;
//			}
//		}
//		return null;

		Optional<TalentDonationProject> project = donationProjectList.stream()
		.filter(p -> p.getTalentDonationProjectName().equals(projectName))
		.findFirst();
		
		return project.orElse(null);
		
	}

	// TO DO
	/**
	 * 새로운 Project 추가
	 * 
	 * @param project 저장하고자 하는 새로운 프로젝트
	 */
	
	/* Controller의 메소드 
	 * 	public void donationProjectInsert(TalentDonationProject project){}
	 * */
	public void donationProjectInsert(TalentDonationProject project) throws Exception {
		TalentDonationProject p = getDonationProject(project.getTalentDonationProjectName());

		if (p != null) {
			throw new Exception("해당 project명은 이미 존재합니다. 재 확인하세요");
		}
		donationProjectList.add(project);
	}
	

	/**
	 * Project의 기부자 수정 - 프로젝트 명으로 검색해서 해당 프로젝트의 기부자 수정
	 * 
	 * @param projectName 프로젝트 이름
	 * @param people      기부자
	 */
	public void donationProjectUpdate(String projectName, Donator people) throws Exception {

//		for (TalentDonationProject project : donationProjectList) {
//			if (project != null && project.getTalentDonationProjectName().equals(projectName)) {
//				if (people != null) {
//					project.setProjectDonator(people);
//					break;
//				} else {
//					throw new Exception("프로젝트 이름은 있으나 기부자 정보 누락 재확인 하세요");
//				}
//
//			} else {
//				throw new Exception("프로젝트 이름과 기부자 정보 재 확인 하세요");
//			}
//		}
		
		TalentDonationProject project = donationProjectList.stream()
				.filter(p -> p != null && p.getTalentDonationProjectName()
				.equals(projectName))
				.findFirst()
				.orElseThrow(() -> new Exception("프로젝트 이름을 제대로 입력해주세요"));
		
		// 프로젝트 업데이트
		if(people != null) {
			project.setProjectDonator(people);
		}else {
			throw new Exception("프로젝트는 있으나 기부자 정보를 누락하였습니다.");
		}
	}

	// TO DO
	/**
	 * Project의 수혜자 수정 - 프로젝트 명으로 검색해서 해당 프로젝트의 수혜자 수정
	 * 
	 * @param projectName 프로젝트 이름
	 * @param people      수혜자
	 */
	public void beneficiaryProjectUpdate(String projectName, Beneficiary people) {

		for (TalentDonationProject project : donationProjectList) {

			if (project != null && project.getTalentDonationProjectName().equals(projectName)) {

				project.setProjectBeneficiary(people);

				break;
			}
		}

	}

	// TO DO
	/**
	 * Project 삭제 - 프로젝트 명으로 해당 프로젝트 삭제
	 * 
	 * @param projectName 삭제하고자 하는 프로젝트 이름
	 */
	public void donationProjectDelete(String projectName) {
		TalentDonationProject project = getDonationProject(projectName);

		if (project != null) {
			donationProjectList.remove(project);
		}

	}

}
