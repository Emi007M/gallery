import { Component, OnInit } from '@angular/core';
import { ImageService } from './image.service';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})


export class AppComponent implements OnInit  {

  images: any[];
  chosenImage = null;
  private blobString:String="";

  brightness = 0;
  contrast = 0;

  constructor(private httpService: ImageService, public domSanitizer: DomSanitizer) { }

  ngOnInit() {
    this.getImages();
  }

  getImages() {
    this.httpService.getImagesFromApi().subscribe(
      data => {
        this.images = data as any[];
      },
      error => {},
      () => console.log('get images from API finished')
    );
  }

  getPath(img) {
    return "data:"+img.type+";base64,"+img.bytes+"";
  }
  getPathBg(img) {
    return "{'background':url("+"data:"+img.type+";base64,"+img.bytes+")}";
  }

  chooseImage(img){
    this.chosenImage = img;
  }

 file: File;
  onChange(event) {
       var files = event.target.files;
        this.file = files[0];

      if (files && this.file) {
        var reader = new FileReader();
        reader.onload =this._handleReaderLoaded.bind(this);
        reader.readAsBinaryString(this.file);

    }
}


_handleReaderLoaded(readerEvt) {
     var binaryString = readerEvt.target.result;
            this.blobString= btoa(binaryString);

        console.log(this.file);
        this.httpService.addImage(this.blobString, this.file.type).subscribe(
          data => {
              console.log("image added", data);
              this.httpService.getImageById(data as number).subscribe(
                data => { this.images.push(data);}
              );

          },
          error => alert(error),
          () => console.log('post image from API finished')
        );
    }


/*getAlteredImages*/
getImage(id, kind){
  if(!this.chosenImage) {return;}
  this.httpService.getImageById(id, kind).subscribe(
    data => {
      console.log(kind+" image received", data);
      this.chooseImage(data);
    }
  );
}

/*change brightness*/
changeBrightness(id, val){
  this.brightness = val;
  if(!this.chosenImage) {return;}
  this.contrast = 0;

console.log("brightness ", id, val);
  this.httpService.getImageById(id, 'brightness', val).subscribe(
    data => {
      console.log("brighter image received", data);
      this.chooseImage(data);
    }
  );
}
/*change contrast*/
changeContrast(id, val){
  this.contrast = val;
  if(!this.chosenImage) {return;}
  this.brightness = 0;

  this.httpService.getImageById(id, 'contrast', val).subscribe(
    data => {
      console.log("contrasted image received", data);
      this.chooseImage(data);
    }
  );
}

/*remove image*/
remove(img) {
  this.httpService.deleteImage(img.id).subscribe(
    data => {
      console.log("removed", data);

      if(this.chosenImage && this.chosenImage.id === img.id) { this.chosenImage = null; }

      let index = this.images.findIndex(function(o){
           return o.id === img.id;
      })
      if (index !== -1) this.images.splice(index, 1);

    }
  );
}


}
