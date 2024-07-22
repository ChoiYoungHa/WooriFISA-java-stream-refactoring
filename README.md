# [우리 FISA 3주차 미니 프로젝트] Java 프로젝트 리팩토링✌

---

### 개발팀원👏

- 최영하, 김상민, 박웅빈, 구동길
---

### 학습목적👀

- Lambda Stream API로 자바 코드를 리팩토링 한다.
- Optional을 사용하여 null과 예외를 처리한다.

### 원본코드👏
- 도네이션 프로젝트 검색기능
```
public TalentDonationProject getDonationProject(String projectName) {
	for (TalentDonationProject project : donationProjectList) {
		if (project != null && project.getTalentDonationProjectName().equals(projectName)) {
			return project;
		}
	}
	return null;
}
```

---

### 리팩토링👏
```
public TalentDonationProject getDonationProject(String projectName) {
	Optional<TalentDonationProject> findproject = donationProjectList.stream()
		.filter(project -> project != null && project.getTalentDonationProjectName()
		.equals(projectName)).findFirst();
		
	return findproject.orElse(null);
}
```
---

### 원본코드👍
- 새로운 프로젝트 추가 기능
```
public void donationProjectInsert(TalentDonationProject project) throws Exception {
		TalentDonationProject p = getDonationProject(project.getTalentDonationProjectName());
		if (p != null) {
			throw new Exception("해당 project명은 이미 존재합니다. 재 확인하세요");
		}
		donationProjectList.add(project);
	}
```

---

### 리팩토링👍
```
public void donationProjectInsert(TalentDonationProject project) throws Exception {
	if (donationProjectList.stream()
		.anyMatch(p -> p.getTalentDonationProjectName().equals(project.getTalentDonationProjectName()))) {
		throw new Exception("해당 project명은 이미 존재합니다.");
	    }
	donationProjectList.add(project);
}
```
---
### 원본코드🎉
- 기부자 수정 기능
```
public void donationProjectUpdate(String projectName, Donator people) throws Exception {

		for (TalentDonationProject project : donationProjectList) {
			if (project != null && project.getTalentDonationProjectName().equals(projectName)) {
				if (people != null) {
					project.setProjectDonator(people);
					break;
				} else {
					throw new Exception("프로젝트 이름은 있으나 기부자 정보 누락 재확인 하세요");
				}

			} else {
				throw new Exception("프로젝트 이름과 기부자 정보 재 확인 하세요");
			}
		}
```
---

### 리팩토링🎉
```
public void donationProjectUpdate(String projectName, Donator people) throws Exception {	
		TalentDonationProject project = donationProjectList.stream()
				.filter(p -> p != null && p.getTalentDonationProjectName()
				.equals(projectName))
				.findFirst()
				.orElseThrow(() -> new Exception("프로젝트 이름을 제대로 입력해주세요"));
		
		if(people != null) {
			project.setProjectDonator(people);
		}else {
			throw new Exception("프로젝트는 있으나 기부자 정보를 누락하였습니다.");
		}
	}
```
---
### 원본코드💖
- 수혜자 수정 기능
```
public void beneficiaryProjectUpdate(String projectName, Beneficiary people) {
		for (TalentDonationProject project : donationProjectList) {
			if (project != null && project.getTalentDonationProjectName().equals(projectName)) {
				project.setProjectBeneficiary(people);
				break;
			}
		}
}
```
---

### 리팩토링💖
```
public void beneficiaryProjectUpdate(String projectName, Beneficiary people) {
	donationProjectList.stream()
	.filter(project -> project != null && project.getTalentDonationProjectName().equals(projectName))
	.findFirst().ifPresent(project1 -> project1.setProjectBeneficiary(people));
}
```
