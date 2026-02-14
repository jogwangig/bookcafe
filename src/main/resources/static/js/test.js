const url = "https://openlibrary.org/search.json?title=how+the+mind+works&language=eng&fields=title,author_name,isbn,cover_i";


const coverUrl = "https://covers.openlibrary.org/b/isbn/0385472579-S.jpg";

const createdUrl = createUrl("how the mind works", "Steven Pinker", "638999");
console.log(createdUrl);

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
						
	

	

	
const p = document.getElementById('btn');

const img = document.getElementById('img');



async function queryApi(){
	
	const url = "https://openlibrary.org/search.json?title=how+the+mind+works&language=eng&fields=title,author_name,isbn,cover_i";

	const options = {
	    method: 'GET'
	};
	
	try{
		const response = await fetch(url, options)
								    .then(response => response.json());

		
		const result = response.docs.reduce((a, c) =>{
			if('cover_i' in c){
					a.push(c);
				}
				
			return	a;
			
		}, []);
		
		const result2 = result.reduce((a,c)=>{
			
			if('cover_i' in c){
				a.push(
					"https://covers.openlibrary.org/b/id/" + c.cover_i + "-M.jpg"
				);
			}
			
			return a;
		}, []);
		
		const container = document.querySelector('.content-fragment');
		
		result2.forEach((url)=>{
			const img = document.createElement('img');
			img.src = url;
			container.appendChild(img);
			
		});
		
		
		p.innerHTML = result2;

		
		const url2 = "https://covers.openlibrary.org/b/id/" + p.innerHTML + "-M.jpg";
		
		/*
		p.innerHTML = url2;
		
		img.src = url2;
		
		*/
		
								
	}catch(error){
		console.error(error);
	}
}


function createUrl(title, author_name, isbn){
	const queryTitle = title.replaceAll(" ", "+");
	const queryAuthorName = author_name.replaceAll(" ", "+");
	const queryIsbn = isbn.replaceAll(" ", "+");
	
	const result = "https://openlibrary.org/search.json?";
	
	return "https://openlibrary.org/search.json?" + "title=" + queryTitle
										+ "&fields=title,author_name,isbn,cover_i";
	 
	/*
	+ "&author_name=" + queryAuthorName +"&isbn=" + queryIsbn;
	"title=" + queryTitle
	*/
}



function filterCoverId(docs){
	const result = docs.reduce((a, c) =>{
				if('cover_i' in c)
						a.push(c);
						
				return	a;
			}, [])
				
				.reduce((a,c)=>{
						if('cover_i' in c)	
							a.push("https://covers.openlibrary.org/b/id/" + c.cover_i + "-M.jpg");
								
						return a;
					}, []);
					
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




p.addEventListener('click', queryApi);



