# [우리FISA 3주차 미션] Java 프로젝트 리팩토링✌

---

### 개발팀원👏

- [최영하](https://github.com/ChoiYoungHa) [김상민](https://github.com/isshomin) [박웅빈](https://github.com/Ungbbi) [구동길](https://github.com/dkac0012)
---

### 학습목적👀

- Lambda Stream API를 사용하여 더욱 효율적인 코드로 리팩토링 한다.
- Optional을 사용하여 null 예외를 처리한다.

### 원본코드👏
- 도네이션 프로젝트 검색기능
```java
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
```java
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
```java
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
```java
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
```java
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
```java
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
```java
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
```java
public void beneficiaryProjectUpdate(String projectName, Beneficiary people) {
	donationProjectList.stream()
	.filter(project -> project != null && project.getTalentDonationProjectName().equals(projectName))
	.findFirst().ifPresent(project1 -> project1.setProjectBeneficiary(people));
}
```

### 고찰 🧐
- stream api를 더 잘 활용할 수 있다면 코드의 간결성과 가독성을 향상시킬 수 있을 것 같다.
- 같은 stream api를 사용하더라도 사용자마다 다른 방식으로 refactoring 되는 것을 통해 다양한 코드를 비교할 수 있으며 생각치 못한 방식도 배우게 되었다.
- stream api를 사용하면 각 클래스마다의 함수를 선언하여 호출 할 필요가 없다는 점에서 일관성 유지가 편할 것 같다고 생각이 들었다.
- stream api의 함수들 중 일부는 이름만 보고 기능을 유추해내기 어려운 것이 있기에  stream api를 자주 사용해보며 어떤 기능들이 들어있는지 알아나가야겠다.
