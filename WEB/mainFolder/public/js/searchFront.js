window.addEventListener('load', async ()=>{
  console.log(document.body.children.length);
  const search = await axios.get('/search_data');
  const searchBox = document.querySelector('.search-people-box');
  const searchUlTag = document.querySelector('.search-people-list');

  let searchHTMLData;
  if(search.data.length === 0) {
    const noDataElement = document.createElement('p');
    noDataElement.style.fontSize="2rem";
    noDataElement.style.color="#666";
    noDataElement.style.width = "100%";
    noDataElement.style.textAlign ="center";
    noDataElement.innerHTML = '검색 결과가 없습니다.'
    searchBox.removeChild(searchUlTag);
    searchBox.insertBefore(noDataElement, searchBox.firstChild);
  } else {
    const searchHTML = await fetch('../lib/search');
    if (searchHTML.status === 200) {
      searchHTMLData = await searchHTML.text();
    };
    for(let i=0; i<search.data.length; i++){
      searchUlTag.innerHTML += searchHTMLData;

    }
    const searchLiTag = document.querySelectorAll('.search-people-item');
    for(let i=0; i<search.data.length; i++){
      searchLiTag[i].id = `${search.data[i].id}-${i}`
      searchLiTag[i].children[0].style.backgroundImage=`url('../data/${search.data[i].id}/1.jpg')`;
      searchLiTag[i].children[1].innerHTML = `${search.data[i].nickname}`;
      if(search.data[i].following_id) {
        searchLiTag[i].children[2].innerHTML = `팔로잉`
        searchLiTag[i].children[2].style.color="crimson";
      }else {
        searchLiTag[i].children[2].innerHTML = `팔로우`
      }
      searchLiTag[i].children[2].addEventListener('click', async ()=>{
        if(searchLiTag[i].children[2].innerHTML.startsWith('팔로우')){
          searchLiTag[i].children[2].innerHTML = '팔로잉'
          searchLiTag[i].children[2].style.color='crimson';
          const followingIndex = searchLiTag[i].id;
          await axios.post('/add_following', { followingIndex })
        } else {
          searchLiTag[i].children[2].innerHTML = '팔로우'
          searchLiTag[i].children[2].style.color='dodgerblue';
          const followingIndex = searchLiTag[i].id;
          await axios.post('/cancel_following', {followingIndex})
        }
      })
    }

  }
})

