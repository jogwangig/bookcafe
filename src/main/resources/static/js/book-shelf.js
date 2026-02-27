async function deleteBookShelf(id){
	if(!confirm("정말 삭제하시겠습니까?")) return;
	
	const token = document.querySelector("meta[name='_csrf']").content;
	const header = document.querySelector("meta[name='_csrf_header']").content;
	
	const option = {
		
		method : 'DELETE',
		
		headers: {
		        [header]: token
		    }
	}
	
	const res =await fetch('/book-shelf/delete?bookShelfId=' + id, option)
							.catch(err=>console.error(err));
							
	const data = await res.json();	
														
	alert(data.msg);
	
	if(res.ok)
		location.href = '/library';			
	
}


const deleteBtns = document.querySelectorAll("button");

deleteBtns.forEach(b=>{
	b.addEventListener('click' , (event)=>{
		const id = event.target.dataset.id;
		
		deleteBookShelf(id);
	});
});


