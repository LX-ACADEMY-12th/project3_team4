<template>
  <div class="signup-container-custom">

    <header class="text-center mb-4">
      <div class="logo-wrapper mx-auto mb-3">
        <img src="@/assets/images/lx.png" alt="LX 로고" class="logo-img">
      </div>
      <h1 class="title mb-1">XIIWORLD</h1>
      <p class="subtitle mb-0">회원가입</p>
    </header>

    <div class="w-100">
      <div class="welcome-text text-center mb-3">XIIWORLD에 가입하세요!</div>
      
      <form @submit.prevent="signup">
        <div class="d-flex gap-1 align-items-center mb-2">
          <input type="text" placeholder="아이디" v-model="loginInfo.loginId" @input="onLoginIdChange" class="form-control form-control-sm cyworld-input flex-fill"
            required>
          <button type="button" @click="checkDuplicate" :class="['btn', 'btn-sm', 'cyworld-check-btn', {
            'cyworld-btn-available': isIdAvailable,
            'cyworld-btn-duplicate': isDuplicateChecked && !isIdAvailable
          }]" :disabled="!loginInfo.loginId">
            {{ buttonText }}
          </button>
        </div>

        <div class="mb-2">
          <input type="text" placeholder="닉네임" v-model="loginInfo.nickname" class="form-control form-control-sm cyworld-input" required>
        </div>

        <div class="mb-2">
          <input type="password" placeholder="비밀번호" v-model="loginInfo.loginPw" class="form-control form-control-sm cyworld-input" required>
        </div>

        <div class="mb-3">
          <input type="password" placeholder="비밀번호 확인" v-model="loginInfo.loginPwConfirm" class="form-control form-control-sm cyworld-input" required>
        </div>

        <div class="d-flex gap-1 mb-3">
          <button type="submit" class="btn btn-sm cyworld-btn-primary flex-fill">가입</button>
          <button type="button" @click="$router.push('/')" class="btn btn-sm cyworld-btn-secondary flex-fill">로그인</button>
        </div>
      </form>
      
      <div class="info-box text-center">
        <div class="team-info">team4</div>
        <div class="stats">
          <span>새로운 멤버를 환영합니다!</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
// 필요한 Vue 라이브러리 및 외부 라이브러리를 가져옵니다.
import { ref, computed } from 'vue' // 'ref'는 반응형 상태를, 'computed'는 계산된 속성을 만듭니다.
import { useRouter } from 'vue-router' // 라우터 인스턴스를 가져와 페이지 이동에 사용합니다.
import axios from 'axios' // 서버와 통신하기 위한 HTTP 클라이언트 라이브러리입니다.

// 라우터 인스턴스를 가져와 `router` 변수에 할당합니다.
const router = useRouter()

// ref()를 사용해 폼 데이터를 반응형 객체로 만듭니다.
// 이 객체의 속성들이 변경되면, 템플릿의 입력 필드와 동기화됩니다.
const loginInfo = ref({
  loginId: '',          // 사용자가 입력할 아이디
  nickname: '',         // 사용자가 입력할 닉네임
  loginPw: '',          // 사용자가 입력할 비밀번호
  loginPwConfirm: ''    // 비밀번호 확인용 입력값
})

// 중복확인 상태를 관리하는 반응형 변수들.
// 이 변수들을 통해 UI(버튼 색상, 텍스트 등)를 동적으로 제어합니다.
const isDuplicateChecked = ref(false) // 중복확인 버튼을 클릭했는지 여부 (클릭 시 true로 변경)
const isIdAvailable = ref(false)      // 중복확인 결과 아이디가 사용 가능한지 여부

// computed 속성을 사용하여 중복확인 버튼의 텍스트를 동적으로 결정합니다.
// 의존하는 반응형 변수(isDuplicateChecked, isIdAvailable)가 변경되면 자동으로 재계산됩니다.
const buttonText = computed(() => {
  // 중복확인을 하지 않은 상태이면 '중복확인' 텍스트를 보여줍니다.
  if (!isDuplicateChecked.value) return '중복확인'
  // 중복확인 후 아이디가 사용 가능하면 '확인완료', 아니면 '중복' 텍스트를 보여줍니다.
  return isIdAvailable.value ? '확인완료' : '중복'
})

/**
 * 아이디 중복을 체크하는 비동기 함수입니다.
 * 사용자가 입력한 아이디를 서버에 보내 사용 가능 여부를 확인합니다.
 */
const checkDuplicate = async () => {
  // 사용자가 아이디를 입력했는지 유효성 검사. .trim()으로 공백을 제거합니다.
  if (!loginInfo.value.loginId.trim()) {
    alert('아이디를 입력해주세요.')
    return // 함수 실행을 중단합니다.
  }
  try {
    // Axios를 사용해 백엔드의 'checkDuplicate' API에 POST 요청을 보냅니다.
    const response = await axios.post('http://localhost:8080/api/checkDuplicate', { loginId: loginInfo.value.loginId })

    // 서버 응답이 'available'이면 true, 아니면 false를 반환하는 변수.
    const result = response.data === 'available'
    // 사용자에게 결과를 알림창으로 보여줍니다.
    alert(result ? '사용 가능한 아이디입니다.' : '이미 사용 중인 아이디입니다.')

    // 중복확인 상태를 업데이트하여 UI를 변경합니다.
    isDuplicateChecked.value = true
    isIdAvailable.value = result // 서버 응답 결과에 따라 아이디 사용 가능 여부를 설정합니다.
  } catch (error) {
    // 서버 연결 실패 또는 기타 오류 발생 시
    console.error('중복 체크 오류:', error)
    alert('서버 연결에 실패했습니다.')
    // 오류 발생 시 중복확인 상태를 초기화합니다.
    isDuplicateChecked.value = false
    isIdAvailable.value = false
  }
}

/**
 * 아이디 입력값이 변경될 때 호출되는 함수입니다.
 * 사용자가 아이디를 수정하면 중복확인 상태를 초기화하여 다시 확인하도록 만듭니다.
 */
const onLoginIdChange = () => {
  isDuplicateChecked.value = false
  isIdAvailable.value = false
}

/**
 * 회원가입을 처리하는 비동기 함수입니다.
 * 모든 유효성 검사를 통과하면 서버에 회원가입 요청을 보냅니다.
 */
const signup = async () => {
  // 모든 필수 필드가 입력되었는지 확인하는 1차 유효성 검사.
  if (!loginInfo.value.loginId.trim() || !loginInfo.value.nickname.trim() || !loginInfo.value.loginPw.trim() || !loginInfo.value.loginPwConfirm.trim()) {
    alert('모든 필드를 입력해주세요.')
    return
  }

  // 아이디 중복확인 여부와 사용 가능 상태를 확인하는 2차 유효성 검사.
  if (!isDuplicateChecked.value || !isIdAvailable.value) {
    alert('아이디 중복확인을 먼저 해주세요.')
    return
  }

  // 비밀번호와 비밀번호 확인 값이 일치하는지 확인하는 3차 유효성 검사.
  if (loginInfo.value.loginPw !== loginInfo.value.loginPwConfirm) {
    alert('비밀번호가 일치하지 않습니다.')
    return
  }

  try {
    // Axios를 사용해 백엔드의 'insert' API에 POST 요청을 보냅니다.
    // 회원가입에 필요한 아이디, 닉네임, 비밀번호 데이터를 전송합니다.
    const response = await axios.post('http://localhost:8080/api/insert', {
      loginId: loginInfo.value.loginId,
      nickname: loginInfo.value.nickname,
      loginPw: loginInfo.value.loginPw
    })

    // 서버 응답이 'success'이면 성공, 아니면 실패 처리.
    if (response.data === 'success') {
      alert('회원가입이 완료되었습니다!')
      router.push('/') // 회원가입 성공 후 메인(로그인) 페이지로 이동합니다.
    } else {
      alert('회원가입에 실패했습니다. 다시 시도해주세요.')
    }
  } catch (error) {
    // 서버 통신 중 오류 발생 시
    console.error('회원가입 오류:', error)
    alert('서버 연결에 실패했습니다.')
  }
}
</script>

<style scoped>
/* 전체 페이지 기본 설정 */
body {
  background-color: white !important; 
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif !important; 
}

/* 회원가입 전체 컨테이너 */
.signup-container-custom {
  width: 380px; /* 고정 너비 (싸이월드 팝업창 크기와 유사) */
  height: auto; /* 내용에 따라 높이 자동 조절 */
  background-color: white; /* 깔끔한 흰색 배경 */
  border: 1px solid #d5d5d5; /* 연한 회색 테두리 (둥글지 않은 직선) */
  display: flex; /* flexbox 레이아웃 사용 */
  flex-direction: column; /* 세로 방향으로 요소 배치 */
  align-items: center; /* 가로 중앙 정렬 */
  padding: 20px; /* 내부 여백 */
  box-sizing: border-box; /* padding과 border를 width에 포함 */
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif; 
  margin: 0 auto; /* 페이지 중앙 정렬 */
}

/* 로고 이미지를 감싸는 원형 컨테이너 */
.logo-wrapper {
  width: 50px; /* 원의 너비 */
  height: 50px; /* 원의 높이 */
  border-radius: 50%; /* 완전한 원 만들기 */
  background-color: #ff6600; /* 오렌지 색상 */
  display: flex; /* flexbox로 내부 요소 중앙 정렬 */
  justify-content: center; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
}

/* 로고 이미지 자체 */
.logo-img {
  width: 30px; /* 이미지 너비 */
  height: 30px; /* 이미지 높이 */
  object-fit: contain; /* 비율 유지하면서 컨테이너에 맞춤 */
}

/* 메인 제목 (XIIWORLD) */
.title {
  font-size: 18px !important; 
  font-weight: bold !important; 
  color: #ff6600 !important; /* 오렌지 색상 */
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif !important; 
}

/* 부제목 (회원가입) */
.subtitle {
  font-size: 14px !important; /* 제목보다 작은 글자 크기 */
  color: #666666 !important; /* 회색 텍스트 */
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif !important; 
}

/* 환영 메시지 박스 */
.welcome-text {
  background-color: #fff4e6; /* 연한 오렌지 배경 */
  color: #ff6600; /* 오렌지 텍스트 */
  font-size: 11px; /* 작은 글씨 크기 */
  padding: 8px 12px; /* 내부 여백 (위아래 8px, 좌우 12px) */
  border: 1px solid #ffd4a3; /* 오렌지 계열 테두리 */
  width: 100%; /* 전체 너비 사용 */
  box-sizing: border-box; /* padding을 width에 포함 */
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif;
}

/*  입력 필드 커스터마이징 */
.cyworld-input {
  border: 1px solid #cccccc !important; /* 연한 회색 테두리 */
  background-color: #ffffff !important; /* 흰색 배경 */
  font-size: 11px !important; 
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif !important; 
  color: #333333 !important; /* 진한 회색 텍스트 */
  padding: 6px 8px !important; /* 내부 여백 (위아래 6px, 좌우 8px) */
  border-radius: 0 !important; /* 모서리를 직각으로 */
}

/* 입력 필드에 포커스(클릭/탭)했을 때 */
.cyworld-input:focus {
  outline: none !important; /* 브라우저 기본 포커스 테두리 제거 */
  border-color: #ff6600 !important; /* 테두리를 오렌지로 변경 */
  background-color: #fffef7 !important; /* 배경을 아주 연한 노란색으로 변경 */
  box-shadow: none !important; /* 부트스트랩 기본 그림자 제거 */
}

/* 입력 필드의 placeholder(힌트 텍스트) 스타일 */
.cyworld-input::placeholder {
  color: #999999 !important; /* 연한 회색 */
  font-size: 11px !important; 
}

/* 중복확인 버튼 기본 스타일 */
.cyworld-check-btn {
  white-space: nowrap !important; /* 텍스트를 한 줄로 유지 (줄바꿈 방지) */
  border: 1px solid #cccccc !important; /* 연한 회색 테두리 */
  padding: 6px 8px !important; /* 내부 여백 */
  font-size: 10px !important; 
  color: #333333 !important; /* 진한 회색 텍스트 */
  background-color: #f8f8f8 !important; /* 연한 회색 배경 */
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif !important; 
  border-radius: 0 !important; /* 모서리를 직각으로 */
}

/* 중복확인 버튼에 마우스 올렸을 때 */
.cyworld-check-btn:hover {
  background-color: #eeeeee !important; /* 조금 더 진한 회색 배경 */
  color: #333333 !important;
  border-color: #cccccc !important;
}

/* 아이디 사용 가능할 때 중복확인 버튼 스타일 */
.cyworld-btn-available {
  background-color: #d4edda !important; /* 연한 녹색 배경 */
  color: #155724 !important; /* 진한 녹색 텍스트 */
  border-color: #c3e6cb !important; /* 녹색 테두리 */
}

/* 아이디 중복될 때 중복확인 버튼 스타일 */
.cyworld-btn-duplicate {
  background-color: #f8d7da !important; /* 연한 빨간색 배경 */
  color: #721c24 !important; /* 진한 빨간색 텍스트 */
  border-color: #f5c6cb !important; /* 빨간색 테두리 */
}

/* 가입 버튼 (주요 액션이므로 강조) */
.cyworld-btn-primary {
  background-color: #ff6600 !important; /* 오렌지 배경 */
  color: #ffffff !important; /* 흰색 텍스트 */
  border: 1px solid #ff6600 !important; /* 오렌지 테두리 */
  font-size: 11px !important; /* 작은 글씨 크기 */
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif !important; 
  padding: 6px 12px !important; /* 내부 여백 */
  border-radius: 0 !important; /* 모서리를 직각으로 */
}

/* 가입 버튼에 마우스 올렸을 때 */
.cyworld-btn-primary:hover {
  background-color: #e55a00 !important; /* 조금 더 진한 오렌지 */
  border-color: #e55a00 !important;
  color: #ffffff !important;
}

/* 로그인 버튼 (보조 액션) */
.cyworld-btn-secondary {
  background-color: #f8f8f8 !important; /* 연한 회색 배경 */
  color: #333333 !important; /* 진한 회색 텍스트 */
  border: 1px solid #cccccc !important; /* 연한 회색 테두리 */
  font-size: 11px !important; 
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif !important; 
  padding: 6px 12px !important; /* 내부 여백 */
  border-radius: 0 !important; /* 모서리를 직각으로 */
}

/* 로그인 버튼에 마우스 올렸을 때 */
.cyworld-btn-secondary:hover {
  background-color: #eeeeee !important; /* 조금 더 진한 회색 */
  border-color: #cccccc !important;
  color: #333333 !important;
}

/* 하단 정보 박스 */
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

/* 팀 정보 텍스트 (team4) */
.team-info {
  font-size: 11px; 
  color: #ff6600; /* 오렌지 색상으로 강조 */
  font-weight: bold; /* 굵은 글씨 */
  margin-bottom: 8px; /* 아래쪽 여백 */
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif; 
}

/* 정보 행들 */
.stats {
  margin: 3px 0; /* 위아래 3px 여백 */
  font-family: "굴림", Gulim, "돋움", Dotum, Arial, sans-serif; 
}

</style>