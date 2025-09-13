<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()

// 폼 데이터
const loginInfo = ref({
  loginId: '',
  nickname: '',
  password: '',
  passwordConfirm: ''
})

// 회원가입 함수
const signup = async () => {
  try {
    // 입력값 검증
    if (!loginInfo.value.loginId || !loginInfo.value.nickname || !loginInfo.value.password || !loginInfo.value.passwordConfirm) {
      alert('모든 필드를 입력해주세요.')
      return
    }

    if (loginInfo.value.password !== loginInfo.value.passwordConfirm) {
      alert('비밀번호가 일치하지 않습니다.')
      return
    }

    // API 호출
    const response = await axios.post('http://localhost:8080/api/insert', {
      loginId: loginInfo.value.loginId,
      nickname: loginInfo.value.nickname,
      password: loginInfo.value.password
    })

    // 응답 처리
    if (response.data === 'success') {
      alert('회원가입이 완료되었습니다!')
      router.push('/login') // 로그인 페이지로 이동
    } else if (response.data === 'duplicate') {
      alert('이미 사용 중인 아이디입니다.')
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

// 아이디 중복 체크 함수 (옵션)
const checkDuplicate = async () => {
  if (!loginInfo.value.loginId) {
    alert('아이디를 입력해주세요.')
    return
  }

  try {
    // 중복 체크를 위한 별도 API가 있다면 여기서 호출
    // 현재는 회원가입 시에만 중복 체크를 하므로 임시로 메시지만 표시
    alert('회원가입 시 중복 체크가 진행됩니다.')
  } catch (error) {
    console.error('중복 체크 오류:', error)
    alert('중복 체크에 실패했습니다.')
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
          type="email"
          placeholder="아이디(이메일)"
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
          v-model="loginInfo.password"
        >
      </div>

      <div>
        <input
          type="password"
          placeholder="비밀번호 확인"
          v-model="loginInfo.passwordConfirm"
        >
      </div>

      <div style="display: flex; gap: 10px; align-items: center;">
        <input
          type="button"
          value="가입"
          @click="signup"
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
