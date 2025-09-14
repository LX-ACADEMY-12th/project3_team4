///
/// 페이지네이션을 위한 Composable 모듈
///
export const usePagination = () => {
  ///
  /// 페이지네이션 객체를 만들기 위한 함수
  ///
  const makePagination = (header) => {
    console.log(`makePagination 호출됨 : ${JSON.stringify(header)}`)

    const pagination = {}

    let curPage = Number(header.page)
    let perPage = Number(header.perPage)
    let totalRecords = header.total
    console.log(`헤더 : ${curPage}, ${perPage}, ${totalRecords}`)

    let pageCount = Math.ceil(totalRecords / perPage)
    console.log(`페이지 개수 : ${pageCount}`) // 한 번에 보여줄 페이지 개수

    let unitPage = 10 // 시작 페이지의 인덱스 (1부터 시작하는 페이지 번호)

    let startPage = Math.floor((curPage - 1) / unitPage) * unitPage + 1 // 끝 페이지

    let endPage = startPage + unitPage - 1
    if (endPage > pageCount) {
      endPage = pageCount
    }

    console.log(`시작 페이지와 끝 페이지 : ${startPage}, ${endPage}`)

    const pages = [] // 수정된 루프: 시작 페이지부터 끝 페이지까지 반복
    for (let i = startPage; i <= endPage; i++) {
      pages.push({
        pageNo: i,
        pageActive: i === curPage,
      })
    }

    pagination.page = curPage
    pagination.perPage = perPage
    pagination.total = totalRecords

    pagination.pages = pages
    pagination.pageCount = pageCount
    return pagination
  }

  return { makePagination }
}
