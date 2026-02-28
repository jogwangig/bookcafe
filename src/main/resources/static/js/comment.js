async function modifyComment(id, authorType){
		
	const token = document.querySelector("meta[name='_csrf']").content;
	const header = document.querySelector("meta[name='_csrf_header']").content;
	
	const commentForm = document.querySelector('.comment-form');
	
	let res;
	
			
	if(authorType == 'user'){
				
		const option = {
			method : 'GET',
						
			headers: { [header]: token }
		};
					
		res = await fetch('/api/post/comment/modify?commentId=' + id, option);
				
	}else{
			
		const postPwd = prompt("비밀번호를 입력해 주세요", "");
				
		const anonymousOption = {
										
			method : 'POST',
					
			headers: {
				[header]: token,
				'Content-Type': 'application/json; charset=utf-8'},
										
				body : JSON.stringify({pwd : postPwd })	
			};
				
			res= await fetch('/api/post/comment/modify?commentId=' + id, anonymousOption);
				
		}
				
		const data = await res.json();
				
		alert(data.msg);
				
		if(res.ok){									
			commentForm.elements['content'].value = data.data.content;
			console.log(data.content);
			
			submitType = 'modify';
			commentId = id;

			document.getElementById("comment-form").scrollIntoView({ behavior: "smooth" });
		}
				
				
			/*
			}else{
				
				const postPwd = prompt("비밀번호를 입력해 주세요", "");
							
				const anonymousOption = {
						
						method : 'POST',
						
						headers: {
						        [header]: token,
								'Content-Type': 'application/json; charset=utf-8' ,
								
								
						    },
						body : JSON.stringify({
								pwd : postPwd
							})
						
					};
					
					
				
				const res= await fetch('/api/post/comment/modify?commentId=' + id, anonymousOption);
				
				const data = await res.json();
				
				alert(data.msg);
				
				if(res.ok){
					commentForm.elements['content'].value = data.content;
				}
					
			}
			*/

}


async function deleteComment(id, authorType){
	
	if(!confirm("정말 삭제하시겠습니까?")) return;
		
	const token = document.querySelector("meta[name='_csrf']").content;
	const header = document.querySelector("meta[name='_csrf_header']").content;
		
	let res;
						
			
	if(authorType == 'user'){
		
		const option = {
			
			method : 'GET',
			
			headers: { [header]: token}
		}
		
		res = await fetch('/api/post/comment/delete?commentId=' + id, option);
			
	}else{
				
		const postPwd = prompt("비밀번호를 입력해 주세요", "");
							
		const anonymousOption = {
						
			method : 'POST',
						
			headers: {  [header]: token, 'Content-Type': 'application/json; charset=utf-8' },
				
			body : JSON.stringify({ pwd : postPwd})
						
		};
						
		res= await fetch('/api/post/comment/delete?commentId=' + id, anonymousOption);
						
		}
		
		const data = await res.json();
				
		alert(data.msg);
		
		if(res.ok)	
			location.reload();

}



const commentForm = document.querySelector(".comment-form");

let submitType = 'create';
let commentId;

async function createComment(event){
		
	event.preventDefault();
	
	const postId = commentForm.dataset.postId;
	
	const formData = new FormData(commentForm);
	
	
	const csrf = formData.get('_csrf');
	
	const data = Object.fromEntries(formData.entries());
	
	delete data._csrf;
	
			
	const option = {
		method : 'POST',
		headers : {
			'Content-type' : 'application/json',
			'X-CSRF-TOKEN' : csrf
		},
		body : JSON.stringify(data)
	};
	
	let res;
	if(submitType == 'create'){
		res = await fetch('/api/post/comment?type=create&postId='+postId, option);
	}else{
		res = await fetch('/api/post/comment?type=modify&commentId='+commentId, option);
		submitType = 'create';
		commentId = '';
	}
	
	if(res.ok)
		alert("성공");
	
	location.reload();
}









commentForm.addEventListener('submit', createComment);

const modifyBtns = document.querySelectorAll(".comment-modify");
const deleteCommentBtns = document.querySelectorAll(".comment-delete");

modifyBtns.forEach(b=>{
	b.addEventListener('click' , (event)=>{
		const id = event.target.dataset.id;
		const authorType = event.target.dataset.authorType;
		
		modifyComment(id , authorType);
	});
});


deleteCommentBtns.forEach(b=>{
	b.addEventListener('click' , (event)=>{
		const id = event.target.dataset.id;
		const authorType = event.target.dataset.authorType;
		
		deleteComment(id , authorType);
	});
});



