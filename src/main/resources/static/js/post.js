const token = document.querySelector("meta[name='_csrf']").content;
const header = document.querySelector("meta[name='_csrf_header']").content;

async function deletePost(id, authorType, boardId){
	
	if(!confirm("정말 삭제하시겠습니까?")) return;
	
	let res;
			
	if(authorType == 'user'){
		
		const option = {
			method : 'DELETE',
			headers: {[header]: token}
		}
		
		res = await fetch('/api/post/delete?postId=' + id, option);
						
	}else{
				
		const postPwd = prompt("비밀번호를 입력해 주세요", "");
							
		const anonymousOption = {
			method : 'POST',
			headers: { [header]: token, 'Content-Type': 'application/json'},
			body : JSON.stringify({pwd : postPwd})
		};
					
		res= await fetch('/api/post/delete?postId=' + id, anonymousOption);
										
	}
			
		const data = await res.json();
			
		alert(data.msg);
					
		if(res.ok)
			location.href = '/board?boardId='+ boardId;
}

async function modifyPost(id, authorType){
	
	let res;
	
	if(authorType == 'user'){
		const option = {
			method : 'GET',		
			headers: { [header]: token } 
		};
		
		res = await fetch('/api/post/modify/auth?postId=' + id, option);
	}else{
		
		const postPwd = prompt("비밀번호를 입력해 주세요", "");
				
		const anonymousOption = {							
			method : 'POST',		
			headers: {
				[header]: token,
				'Content-Type': 'application/json; charset=utf-8'},					
				body : JSON.stringify({pwd : postPwd })	
		};
		
		res= await fetch('/api/post/modify/auth?postId=' + id, anonymousOption);
	}
	
	const data = await res.json();
					
	alert(data.msg);
	
	if(res.ok)
		location.href = '/post/modify?postId='+ id;
}





const deleteBtns = document.querySelectorAll(".post-delete");

const postModifyBtns = document.querySelectorAll(".post-modify");

deleteBtns.forEach(b=>{
	b.addEventListener('click' , (event)=>{
		const id = event.target.dataset.id;
		const authorType = event.target.dataset.authorType;
		const boardId = event.target.dataset.boardId;
		
		deletePost(id , authorType, boardId);
	});
});


postModifyBtns.forEach(b=>{
	b.addEventListener('click' , (event)=>{
		const id = event.target.dataset.id;
		const authorType = event.target.dataset.authorType;
		
		modifyPost(id , authorType);
	});
});


