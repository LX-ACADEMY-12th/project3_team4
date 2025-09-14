<script setup>

</script>

<template>
 

<div style="width: 400px; height: 400px; background-color: white; border-radius: 50%; display: flex; flex-direction: column; justify-content: center; align-items: center; padding: 40px; box-sizing: border-box;">

  <div style="display: flex; align-items: center;justify-content: center;  margin-bottom: 20px; gap: 15px">
    <div style="width: 40px; height: 40px; border-radius: 50%; background-color: black; border: 2px solid #ddd; display: flex; justify-content: center; align-items: center; margin: 0 auto 10px auto; overflow: hidden;">
      <img src="@/assets/images/lx.png" style="width: 40px; height: 40px; object-fit: contain;">
    
    </div>
    <div style="font-size:xx-large; font-weight: bold; color:gray;">XIIWORLD</div>
    <div style="color:gray; font-size: smaller;">team4</div>
  </div>
  <div style="color:gray; font-weight: bold; ">회원가입</div>
  <div style="display: flex; flex-direction: column; gap: 10px; width: 300px; align-items: center;">
  
    <div style="display: flex; gap: 10px; align-items: center;">
      <input type="text" placeholder="아이디(이메일)" style="flex: 1; ">
      <input type="button" value="중복">
    </div>
    <div>
      <input type="text" placeholder="닉네임">
    </div>
    <div>
      <input type="text" placeholder="비밀번호">
    </div>
    <div>
      <input type="text" placeholder="비밀번호 확인">
    </div>
     <div style="display: flex; gap: 10px; align-items: center;">
       <input type="button" value="가입">
      <input type="button" value="로그인"  @click="$router.push('/about')">
    </div>
   
  </div>
</div>
</template>
