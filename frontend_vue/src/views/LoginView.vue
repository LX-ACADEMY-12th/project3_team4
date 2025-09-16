<template>
  <!-- 부트스트랩 클래스로 변환된 싸이월드 스타일 로그인 -->
  <div class="container-custom">

    <header class="text-center mb-4">
      <div class="logo-wrapper mx-auto mb-3">
        <img src="@/assets/images/lx.png" alt="LX 로고" class="logo-img">
      </div>
      <h1 class="title mb-1">XIIWORLD</h1>
      <p class="subtitle mb-0">로그인하기</p>
    </header>

    <div class="w-100">
      <div class="welcome-text text-center mb-3">XIIWORLD에 로그인 하세요!</div>
      
      <form @submit.prevent="handleLogin">
        <div class="mb-2">
          <input type="text" placeholder="아이디(이메일)" v-model="loginData.loginId" class="form-control form-control-sm cyworld-input" required>
        </div>
        <div class="mb-3">
          <input type="password" placeholder="비밀번호" v-model="loginData.loginPw" class="form-control form-control-sm cyworld-input" required>
        </div>

        <div class="d-flex gap-1 mb-3">
          <button type="submit" class="btn btn-sm cyworld-btn-primary flex-fill">로그인</button>
          <button type="button" @click="$router.push('/signup')" class="btn btn-sm cyworld-btn-secondary flex-fill">회원가입</button>
        </div>
      </form>
      
      <div class="info-box text-center">
        <span>다시 돌아온 XIIWORLD 환영합니다.</span>
      </div>
    </div>
  </div>
</template>

<script setup>
// 필요한 Vue 라이브러리 및 외부 라이브러리를 가져옵니다.
import { ref } from 'vue' // 반응형 상태 관리를 위한 ref 함수
import { useRouter } from 'vue-router' // 라우터 객체 사용을 위한 훅
import axios from 'axios' // HTTP 통신을 위한 Axios 라이브러리

// 라우터 인스턴스를 가져와 router 변수에 할당.
const router = useRouter()

// ref()를 사용해 loginData 객체를 반응형으로 만듭니다.
// 이렇게 하면 loginData의 값이 변경될 때 템플릿도 자동으로 업데이트됩니다.
const loginData = ref({
  loginId: '',
  loginPw: ''
})

/**
 * 로그인 버튼 클릭 시 실행되는 비동기 함수.
 * 서버에 로그인 요청을 보냅니다.
 */
async function handleLogin() {
  try {
    // Axios를 사용하여 'http://localhost:8080/api/login' 엔드포인트에 POST 요청을 보냅니다.
    // loginData.value 객체 전체를 JSON 형태로 서버에 전송합니다.
    const response = await axios.post('http://localhost:8080/api/login', loginData.value)

    // 서버 응답의 상태 코드를 확인. 200은 성공을 의미합니다.
    if (response.status === 200 && response.data) {
      alert('로그인 성공')
      // 로그인 성공 시 사용자의 아이디를 세션 스토리지에 저장.
      // 이렇게 저장된 값은 다른 페이지에서 사용자의 로그인 상태를 확인하는 데 사용됩니다.
      const userData = response.data;
      sessionStorage.setItem('loginId', userData.loginId);
      sessionStorage.setItem('userId', userData.userId); // 실제 DB의 PK
      sessionStorage.setItem('nickname', userData.nickname);

      // 로그인 성공 후 'minihome' 경로로 페이지를 이동시킵니다.
      router.push(`/minihome/${userData.loginId}`)
    } else {
      // 200 OK가 아닌 다른 성공 응답(예: 204 No Content)이 올 경우 처리.
      alert('로그인 실패: 서버 응답 오류')
      console.error('서버 응답 오류:', response.status, response.data)
    }

  } catch (error) {
    // 요청 실패 시(네트워크 오류, 4xx, 5xx 에러 등) 이 블록이 실행됩니다.

    // axios 에러 객체의 response 속성을 확인하여 서버 응답이 있었는지 확인.
    if (error.response) {
      // 서버가 응답을 보냈지만, 상태 코드가 2xx 범위가 아닌 경우(예: 401 Unauthorized, 404 Not Found).
      console.error('로그인 에러:', error.response.data)
      // 서버에서 보낸 에러 메시지를 alert로 표시하거나, 기본 메시지를 보여줍니다.
      alert('로그인 실패: ' + (error.response.data || '아이디 또는 비밀번호가 일치하지 않습니다.'))
    } else if (error.request) {
      // 요청이 전송되었으나, 응답을 받지 못한 경우(네트워크 연결 문제, CORS 문제 등).
      console.error('네트워크 에러:', error.request)
      alert('로그인 실패: 네트워크 연결을 확인해주세요.')
    } else {
      // 요청을 보내기 전에 발생한 기타 오류(잘못된 요청 설정 등).
      console.error('기타 에러:', error.message)
      alert('로그인 실패: 알 수 없는 오류가 발생했습니다.')
    }
  }
}
</script>

<style scoped>
/* 전체 페이지 기본 설정 */
body {
  background-color:white !important; 
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif !important; 
}

/* 로그인 폼 전체 컨테이너 (부트스트랩 클래스와 함께 사용) */
.container-custom {
  width: 350px; /* 고정 너비 (당시 해상도에 맞는 작은 크기) */
  height: auto; /* 내용에 따라 높이 자동 조절 */
  background-color: white; /* 깔끔한 흰색 배경 */
  border: 1px solid #d5d5d5; /* 연한 회색 테두리 (둥글지 않은 직선형) */
  display: flex; /* flexbox 레이아웃 사용 */
  flex-direction: column; /* 자식 요소들을 세로 방향으로 배치 */
  align-items: center; /* 가로축 중앙 정렬 */
  padding: 20px; /* 내부 여백 */
  box-sizing: border-box; /* padding과 border를 width에 포함하여 계산 */
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif; /* 폰트 재지정 (확실히 하기 위해) */
  margin: 0 auto; /* 페이지 중앙 정렬 */
}

/* 로고를 감싸는 원형 컨테이너 */
.logo-wrapper {
  width: 50px; /* 원의 너비 */
  height: 50px; /* 원의 높이 */
  border-radius: 50%; /* 완전한 원 만들기 (50% = 원) */
  background-color: #ff6600; /* 시그니처 오렌지 색상 */
  display: flex; /* flexbox로 내부 요소 중앙 정렬 */
  justify-content: center; /* 가로축 중앙 정렬 */
  align-items: center; /* 세로축 중앙 정렬 */
}

/* 로고 이미지 스타일 */
.logo-img {
  width: 30px; /* 이미지 너비 */
  height: 30px; /* 이미지 높이 */
  object-fit: contain; /* 비율 유지하면서 컨테이너에 맞춤 */
}

/* 메인 제목 (XIIWORLD) */
.title {
  font-size: 18px !important; /* 글자 크기 */
  font-weight: bold !important; /* 굵은 글씨 */
  color: #ff6600 !important; /* 오렌지 색상 */
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif !important; 
}

/* 부제목 (로그인하기) */
.subtitle {
  font-size: 14px !important; /* 제목보다 작은 글자 크기 */
  color: #666666 !important; /* 중간 톤의 회색 */
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif !important; 
}

/* 환영 메시지가 들어가는 노란색 박스 */
.welcome-text {
  background-color: #fff4e6; /* 연한 크림/오렌지 배경색 */
  color: #ff6600; /* 오렌지 텍스트 색상 */
  font-size: 11px;
  padding: 8px 12px; /* 내부 여백 (위아래 8px, 좌우 12px) */
  border: 1px solid #ffd4a3; /* 오렌지 테두리 */
  width: 100%; /* 전체 너비 사용 */
  box-sizing: border-box; /* padding을 width에 포함 */
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif;
}

/* 부트스트랩 입력 필드 커스터마이징 (싸이월드 스타일) */
.cyworld-input {
  border: 1px solid #cccccc !important; /* 연한 회색 테두리 */
  background-color: #ffffff !important; /* 흰색 배경 */
  font-size: 11px !important; /* 작은 글씨 크기 */
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif !important; 
  color: #333333 !important; /* 진한 회색 텍스트 색상 */
  padding: 6px 8px !important; /* 내부 여백 (위아래 6px, 좌우 8px) */
  border-radius: 0 !important; /* 모서리 직각 */
}

/* 입력 필드에 포커스(클릭/탭)가 있을 때 */
.cyworld-input:focus {
  outline: none !important; /* 브라우저 기본 포커스 링 제거 */
  border-color: #ff6600 !important; /* 테두리 오렌지 */
  background-color: white !important; 
  box-shadow: none !important; /* 그림자 제거 */
}

/* placeholder 텍스트 (힌트 텍스트) 스타일 */
.cyworld-input::placeholder {
  color: #999999 !important; /* 연한 회색 */
  font-size: 11px !important; /* 작은 글씨 크기 */
}

/* 로그인 버튼 (주요 액션이므로 강조) */
.cyworld-btn-primary {
  background-color: #ff6600 !important; /* 오렌지 배경 */
  color: white !important; /* 흰색 텍스트 */
  border: 1px solid #ff6600 !important; /* 오렌지 테두리 */
  font-size: 11px !important; /* 작은 글씨 크기 */
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif !important; /* 폰트 지정 */
  padding: 6px 12px !important; /* 내부 여백 */
  border-radius: 0 !important; /* 모서리 직각 */
}

/* 로그인 버튼에 마우스를 올렸을 때 */
.cyworld-btn-primary:hover {
  background-color: #e55a00 !important; /* 조금 더 진한 오렌지 */
  border-color: #e55a00 !important;
}

/* 회원가입 버튼 (보조 액션) */
.cyworld-btn-secondary {
  background-color: #f8f8f8 !important; /* 연한 회색 배경 */
  color: #333333 !important; /* 진한 회색 텍스트 */
  border: 1px solid #cccccc !important; /* 연한 회색 테두리 */
  font-size: 11px !important; /* 작은 글씨 크기 */
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif !important; 
  padding: 6px 12px !important; /* 내부 여백 */
  border-radius: 0 !important; /* 모서리를 직각으로 */
}

/* 회원가입 버튼에 마우스를 올렸을 때 */
.cyworld-btn-secondary:hover {
  background-color: #eeeeee !important; /* 조금 더 진한 회색으로 변경 */
  border-color: #cccccc !important;
  color: #333333 !important;
}

/* 하단 정보를 담는 박스 */
.info-box {
  width: 100%; /* 전체 너비 사용 */
  background-color: #f8f9fa; /* 매우 연한 회색 배경 */
  border: 1px solid #e9ecef; /* 연한 회색 테두리 */
  padding: 12px; /* 내부 여백 */
  box-sizing: border-box; /* padding을 width에 포함 */
  font-size: 10px; 
  color: #666666; /* 회색 텍스트 */
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif; 
}
</style>