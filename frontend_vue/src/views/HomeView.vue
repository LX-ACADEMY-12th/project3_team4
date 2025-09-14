<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()

// 폼 데이터
const loginInfo = ref({
  loginId: '',
  nickname: '',
  loginPw: '',
  loginPwConfirm: ''
})

// 중복확인 상태
const isDuplicateChecked = ref(false)
const isIdAvailable = ref(false)

// 아이디 중복 체크 함수
const checkDuplicate = async () => {
  if (!loginInfo.value.loginId) {
    alert('아이디를 입력해주세요.')
    return
  }

  try {
    const response = await axios.post('http://localhost:8080/api/checkDuplicate', {
      loginId: loginInfo.value.loginId
    })

    if (response.data === 'available') {
      alert('사용 가능한 아이디입니다.')
      isDuplicateChecked.value = true
      isIdAvailable.value = true
    } else if (response.data === 'duplicate') {
      alert('이미 사용 중인 아이디입니다.')
      isDuplicateChecked.value = true
      isIdAvailable.value = false
    } else {
      alert('중복확인 중 오류가 발생했습니다.')
      isDuplicateChecked.value = false
      isIdAvailable.value = false
    }

  } catch (error) {
    console.error('중복 체크 오류:', error)
    alert('서버 연결에 실패했습니다.')
    isDuplicateChecked.value = false
    isIdAvailable.value = false
  }
}

// 아이디 입력값이 변경되면 중복확인 상태 초기화
const onLoginIdChange = () => {
  isDuplicateChecked.value = false
  isIdAvailable.value = false
}

// 회원가입 함수 (클라이언트에서 중복확인 체크)
const signup = async () => {
  try {
    // 클라이언트에서 중복확인 체크
    if (!isDuplicateChecked.value || !isIdAvailable.value) {
      alert('아이디 중복확인을 먼저 해주세요.')
      return
    }

    // API 호출
    const response = await axios.post('http://localhost:8080/api/insert', {
      loginId: loginInfo.value.loginId,
      nickname: loginInfo.value.nickname,
      loginPw: loginInfo.value.loginPw
    })

    // 응답 처리
    if (response.data === 'success') {
      alert('회원가입이 완료되었습니다!')
      router.push('/login') // 로그인 페이지로 이동
    } else if (response.data === 'fail') {
      alert('회원가입에 실패했습니다. 다시 시도해주세요.')
    } else {
      alert('오류가 발생했습니다.')
    }

  } catch (error) {
    console.error('회원가입 오류:', error)
    alert('서버 연결에 실패했습니다.')
  }
}
</script>

<template>
  <div style="width: 400px; height: 400px; background-color: white; border-radius: 50%; display: flex; flex-direction: column; justify-content: center; align-items: center; padding: 40px; box-sizing: border-box;">

    <div style="display: flex; align-items: center; justify-content: center; margin-bottom: 20px; gap: 15px">
      <div style="width: 40px; height: 40px; border-radius: 50%; background-color: black; border: 2px solid #ddd; display: flex; justify-content: center; align-items: center; margin: 0 auto 10px auto; overflow: hidden;">
        <img src="@/assets/images/lx.png" style="width: 40px; height: 40px; object-fit: contain;">
      </div>
      <div style="font-size: xx-large; font-weight: bold; color: gray;">XIIWORLD</div>
      <div style="color: gray; font-size: smaller;">team4</div>
    </div>

    <div style="color: gray; font-weight: bold;">회원가입</div>

    <div style="display: flex; flex-direction: column; gap: 10px; width: 300px; align-items: center;">
      <div style="display: flex; gap: 10px; align-items: center;">
        <input
          type="text"
          placeholder="아이디"
          style="flex: 1;"
          v-model="loginInfo.loginId"
          @input="onLoginIdChange"
        >
        <input
          type="button"
          :value="isDuplicateChecked ? (isIdAvailable ? '확인완료' : '중복') : '중복확인'"
          @click="checkDuplicate"
          :style="{
            cursor: 'pointer',
            backgroundColor: isDuplicateChecked ? (isIdAvailable ? '#28a745' : '#dc3545') : '#007bff',
            color: 'white',
            border: 'none',
            padding: '5px 10px',
            borderRadius: '4px',
            fontSize: '12px'
          }"
          :disabled="!loginInfo.loginId"
        >
      </div>

      <div>
        <input
          type="text"
          placeholder="닉네임"
          v-model="loginInfo.nickname"
        >
      </div>

      <div>
        <input
          type="password"
          placeholder="비밀번호"
          v-model="loginInfo.loginPw"
        >
      </div>

      <div>
        <input
          type="password"
          placeholder="비밀번호 확인"
          v-model="loginInfo.loginPwConfirm"
        >
      </div>

      <div style="display: flex; gap: 10px; align-items: center;">
        <input
          type="button"
          value="가입"
          @click="signup()"
          style="cursor: pointer;"
        >
        <input
          type="button"
          value="로그인"
          @click="$router.push('/about')"
          style="cursor: pointer;"
        >
      </div>
    </div>
  </div>
</template>
