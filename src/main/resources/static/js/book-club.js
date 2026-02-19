async function registerBookClub(event){
	
	const target = event.currentTarget;
	
	
	const bookCludId = new URL(target.parentElement.querySelector("a").href)
								.searchParams.get('bookClubId');
		
	await fetch("/api/book-club/register?bookClubId=" + bookCludId)
				.then(res=>res.text())
				.then(data=>console.log(data))
				.catch(err=>console.error(err));
				
	
	target.innerHTML = "등록됨";
	
}


const registerBtns	= document.getElementsByClassName("register-btn");

for(let i = 0; i < registerBtns.length; i++){
	registerBtns[i].addEventListener('click', registerBookClub);
	}





