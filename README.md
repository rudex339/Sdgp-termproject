# SPGP_Term_Project 
스마트폰 게임 프로그래밍 텀 프로젝트 1차 발표

1. **게임 컨셉**

   *타워디펜스*
   
   ![image](https://github.com/rudex339/Sdgp-termproject/assets/58317478/fa447ffc-e4d3-4850-b76a-8d2b091fe850)
   
   2차원 타일에 아군 타워를 설치 가능
   
   타워를 설치할때 랜덤으로 생성되는 자원을 클릭하여 획득한 자원을 소모
   
   타워는 적을 자동으로 공격함
   
   적은 오른쪽에서 왼쪽으로 이동함
   
   적은 타워를 부술수 있다
   
   적이 왼쪽 끝까지 가면 게임 오버, 끝까지 버티면 승리
   

3. **개발 범위**
   
   아군 타워 4종류
   
  ![W_Pawn](https://github.com/rudex339/Sdgp-termproject/assets/58317478/6222e464-8263-48ac-ae72-4e1dcbbcaede)
  기본적으로 원거리 공격을 하고 체력은 10정도 공격력은 3

  ![W_Bishop](https://github.com/rudex339/Sdgp-termproject/assets/58317478/6405d486-cdec-4d7e-8e28-99f2767c207e)
  주변의 아군들에게 체력을 2 회복시킨다 쿨타임 0.5초 체력은 5
   
  ![W_Knight](https://github.com/rudex339/Sdgp-termproject/assets/58317478/d23817fb-7e5a-46f8-a6e1-78e9917ce4ef)
  주변 4칸의 적에게 공격을 한다 체력은 15 공격력은 5

  ![W_Rook](https://github.com/rudex339/Sdgp-termproject/assets/58317478/c8553322-3d65-40d6-b63f-cf46eed62367)
  자신과 같은 칸에 있는 적에게 공격한다 체력은 30 공격력은 1

  적 4종류
  ![B_Pawn](https://github.com/rudex339/Sdgp-termproject/assets/58317478/1c082c11-e97c-4736-8468-e033fbf3dbe6)
  근거리 공격, 체력 5 공격력 2

  ![B_Knight](https://github.com/rudex339/Sdgp-termproject/assets/58317478/1e67cedd-ff6d-4daf-83f9-1095373d5efd)
  근거리 공격, 체력 10, 공격력 5, 이속이 빠르다

  ![B_Bishop](https://github.com/rudex339/Sdgp-termproject/assets/58317478/023244bd-4210-4619-be6b-dc8fecce0fdf)
  원거리 공격, 체력 5, 공격력 7

  ![B_Rook](https://github.com/rudex339/Sdgp-termproject/assets/58317478/094cc5be-3de2-44da-bc06-623ab22f42cd)
  근거리 공격, 체력 20, 공격력 4



   
   타워 강화, 랜덤 생성되는 자원
   
   스테이지 1개/ 3개의 웨이브
   
5. **개발 일정**
   
|주차|개발 내용|비고|
|---|---|---|
|1주차|게임 프레임 워크 제작|시작화면 -> 게임화면 -> 종료 화면 흐름 제작|
|2주차|기본 스테이지 클래스 제작||
|3주차|아군 타워 기본 클래스 제작||
|4주차|적 유닛 기본 클래스 제작||
|5주차|스테이지 제작|스테이지 작동 확인|
|6주차|적 ai 제작, 자원 추가|실게임 플레이 제작|
|7주차|4가지 종류 타워 제작||
|8주차|4가지 적 유닛 추가||
|9주차|디버그||
