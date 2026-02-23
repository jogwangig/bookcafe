async function deleteReadingRecord(id){
	if(!confirm("정말 삭제하시겠습니까?")) return;
	
	const token = document.querySelector("meta[name='_csrf']").content;
	const header = document.querySelector("meta[name='_csrf_header']").content;
	
	const option = {
		
		method : 'DELETE',
		
		headers: {
		        [header]: token
		    }
	}
	
	await fetch('/reading-record/delete?readingRecordId=' + id, option)
			.then(res=>{if(res.ok){
							alert("삭제 성공");
							location.reload();
						}else{
							alert("삭제 실패");
						}
					})
			.catch(err=>console.error(err));	
	
}


const deleteBtns = document.querySelectorAll("button");

deleteBtns.forEach(b=>{
	b.addEventListener('click' , (event)=>{
		const id = event.target.dataset.id;
		
		deleteReadingRecord(id);
	});
});


