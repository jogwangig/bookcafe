const searchUrl = "https://openlibrary.org/search.json?title=how+the+mind+works&language=eng&fields=title,author_name,isbn,cover_i";


const coverImgUrl = "https://covers.openlibrary.org/b/isbn/0385472579-S.jpg";

const createdUrl = createUrl("how the mind works", "", "");
//console.log(createdUrl);

const options = {
    method: 'GET'
};


fetch(createdUrl, options)
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error('Error:', error));
	

 fetch(createdUrl, options)
					  .then(response => response.json())
					    .then(data => filterCoverId(data.docs))
						.then(imgUrls=>fetchCoverImgs(imgUrls))
					    .catch(error => console.error('Error:', error));
						
	

	


function createUrl(title, author_name, isbn){
	
	const conditions = ['title', 'author', 'isbn'];
	
	const conditionValues = [title, author_name, isbn].map(v=>v.replaceAll(" ","+"));
	
	let result = "https://openlibrary.org/search.json?";
	
	let conditionResults = "";
	
	for(let i = 0; i <conditions.length; i++){
		if(conditionValues[i] == "") continue;
				
		if(conditionResults != "") conditionResults += "&";
		
		conditionResults += conditions[i] + "=" + conditionValues[i];

	}
	
	return result + conditionResults + "&fields=title,author_name,isbn,cover_i";
	
}



function filterCoverId(docs){
	const url = "https://covers.openlibrary.org/b/id/";
	const imgSize = "-M.jpg";
	const result = docs.filter(c => 'cover_i' in c)
						.map(c => url + c.cover_i + imgSize);
					
	return result;
}


function fetchCoverImgs(imgUrls){
	const container = document.querySelector('.content-fragment');
			
			imgUrls.forEach((url)=>{
				const img = document.createElement('img');
				img.src = url;
				container.appendChild(img);
				
			});
}



/*
p.addEventListener('click', queryApi);*/



