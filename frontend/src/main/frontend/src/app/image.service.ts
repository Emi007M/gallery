import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class ImageService {
constructor(private http: HttpClient) {}



  getImagesFromApi() {
    const url = 'api/images';
    return this.http.get(url);
  }

  getImageById(id: number, kind='', lvl='') {
    const url = `api/images/${id}/${kind}/${lvl}`;
    return this.http.get(url);
  }

  addImage(blob, type) {
    if(!blob || !type) { return null; }
    const url = 'api/images';
    let img = {
      bytes: blob,
      type: type
    };
    return this.http.post(url, img);
  }

  deleteImage(id) {
    const url = `api/images/${id}`;
    return this.http.delete(url);
  }
}
