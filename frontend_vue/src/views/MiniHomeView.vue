<template>
  <!-- 전체 영역 -->
  <div class="border d-flex vw-100">
    <div class="border m-10 bg-light-success" style="width: 75%;">
      <div class="border-dashed m-5">
        <div class="border m-2 p-2 bg-white">
          <div class="border">
            <div class="d-flex">
              <div class="flex-fill border me-10 d-flex flex-column justify-content-end align-items-center" style="width: 25%;">
                <div class="border">
                  <span>TODAY n명 | TOTAL n명</span>
                </div>
              </div>
              <div class="border" style="width: 75%;">
                  <span class="fs-1">제목</span>
              </div>
            </div>
          </div>
          <div class="border mt-2">
            <div class="d-flex">
              <div class="flex-fill border me-10 d-flex flex-column" style="width: 25%;">
                <div class="m-5 border">
                  <div>
                    <span> TODAY IS (기분)</span>
                  </div>
                  <div class="border ps-" style="height: 200px;">
                    프로필사진
                  </div>
                  <div class="border" style="height: 150px;">
                    멘트
                  </div>
                  <div class="border">
                    history
                  </div>
                  <div class="border">
                    <span>닉네임</span>
                    - 성별아이콘, 생년월일 등...
                  </div>
                  <div class="border">
                    일촌 파도타기
                  </div>
                </div>
              </div>
              <div class="flex-fill d-flex flex-column" style="width: 75%;">
                <!-- 탭영역 -->
                <div class="flex-fill d-flex flex-root app-root border bg-light" id="kt_app_root">
                  탭 선택시 달라질 영역
                  <!--
                    <RouterView />
                   -->
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="flex-fill border mt-10 mb-10 me-10">
      <div class="flex-fill border bg-secondary" style="height: 200px;">
        bgm 영역
      </div>
      <div class="d-flex border justify-content-around">
        <button style="width: 100%;">내 미니홈피 가기</button>
        <button style="width: 100%;">로그아웃</button>
      </div>
    </div>
  </div>
</template>

<script setup>



</script>

<style scoped>

</style>
