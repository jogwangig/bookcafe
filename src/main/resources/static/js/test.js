const url = "https://openlibrary.org/search.json?title=how+the+mind+works&language=eng&fields=isbn";


const url2 = "https://covers.openlibrary.org/b/isbn/0385472579-S.jpg";

const options = {
    method: 'GET'
};

fetch(url, options)
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error('Error:', error));
	
	
const p = document.getElementById('btn');

const img = document.getElementById('img');

if(p == null){
	console.log("왜 null?");
}

async function queryApi(){
	
	const url = "https://openlibrary.org/search.json?title=how+the+mind+works&fields=isbn";

	const options = {
	    method: 'GET'
	};
	
	try{
		const response = await fetch(url, options)
								    .then(response => response.json());
		/*
		p.innerHTML = response.docs[0].author_name.reduce((acc, cur)=>{
			return acc + " : " + cur;
		});*/
		
		p.innerHTML = response.docs[0].isbn[25];
		
		if(response.docs[0].isbn.includes("9780393334777")){
			console.log("포함");
		}
		

		
		const url2 = "https://covers.openlibrary.org/b/isbn/" + p.innerHTML + "-L.jpg";
		
		
		p.innerHTML = url2;
		
		img.src = url2;
		
		
		
								
	}catch(error){
		console.error(error);
	}
}

async function queryImg(){
	
	const url2 = "https://covers.openlibrary.org/b/isbn/" + p.innerHTML + "-S.jpg";

	const options = {
	    method: 'GET'
	};
	
	try{
		const response = await fetch(url2, options)
								    .then(response => response.json());

		
								
	}catch(error){
		console.error(error);
	}
}


p.addEventListener('click', queryApi);



