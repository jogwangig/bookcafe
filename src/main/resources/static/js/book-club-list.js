async function registerBookClub(event){
	
	const target = event.currentTarget;
	
	
	const bookCludId = new URL(target.parentElement.querySelector("a").href)
								.searchParams.get('bookClubId');
		
	const res = await fetch("/api/book-club/register?bookClubId=" + bookCludId)
							.catch(err=>console.error(err));
	
	const data = await res.json();	
	
	alert(data.msg);
				
				
	target.removeEventListener('click', registerBookClub);
	
	target.addEventListener('click', unregisterBookClub);

				
	
	target.innerHTML = "탈퇴하기";
	
}

async function unregisterBookClub(event){
	
	const target = event.currentTarget;
	
	
	const bookCludId = new URL(target.parentElement.querySelector("a").href)
								.searchParams.get('bookClubId');
		
	const res = await fetch("/api/book-club/unregister?bookClubId=" + bookCludId)
							.catch(err=>console.error(err));
							
	const data = await res.json();	

	alert(data.msg);
				
	
	target.removeEventListener('click', unregisterBookClub);
	
	target.addEventListener('click', registerBookClub);
	
	target.innerHTML = "등록하기";
	
}



const registerBtns	= document.getElementsByClassName("register-btn");

for(let i = 0; i < registerBtns.length; i++){
	(registerBtns[i].innerHTML == "등록하기")?
		registerBtns[i].addEventListener('click', registerBookClub)
		:registerBtns[i].addEventListener('click', unregisterBookClub);
		
}





