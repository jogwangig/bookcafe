async function deletePost(id, authorType, boardId){
		
	const token = document.querySelector("meta[name='_csrf']").content;
	const header = document.querySelector("meta[name='_csrf_header']").content;
	
	const option = {
		
		method : 'DELETE',
		
		headers: {
		        [header]: token
		    }
	}
			
			console.log(authorType);
			
			
			if(authorType == 'user'){
				const res = await fetch('/api/post/delete?postId=' + id, option);
				const data = await res.text();
				
				if(res.ok){
					alert(data);
					location.href = '/board?boardId='+ boardId;
				}else{
					alert(data);
				}
				
				
			}else{
				
				const postPwd = prompt("비밀번호를 입력해 주세요", "");
							
				const anonymousOption = {
						
						method : 'POST',
						
						headers: {
						        [header]: token,
								'Content-Type': 'application/json' 
						    },
						body : JSON.stringify({
								pwd : postPwd
							})
						
					};
					
					
				
				const res= await fetch('/api/post/delete?postId=' + id, anonymousOption);
				const data = await res.text();
				
				if(res.ok){
					alert(data);
					location.href = '/board?boardId='+ boardId;
				}else{
					alert(data);
				}
						
			}
	
}





const deleteBtns = document.querySelectorAll(".post-delete");

deleteBtns.forEach(b=>{
	b.addEventListener('click' , (event)=>{
		const id = event.target.dataset.id;
		const authorType = event.target.dataset.authorType;
		const boardId = event.target.dataset.boardId;
		
		deletePost(id , authorType, boardId);
	});
});


