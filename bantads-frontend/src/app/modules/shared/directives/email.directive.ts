import { Validator, NG_VALIDATORS, FormControl, ValidationErrors } from '@angular/forms';
import { Directive, OnInit } from '@angular/core';

@Directive({
  selector: '[email]',
  providers: [{
    provide: NG_VALIDATORS,
    useExisting: EmailDirective,
    multi: true
  }]
})

export class EmailDirective implements Validator, OnInit {

  constructor() { }

  ngOnInit() { }

  validate(c: FormControl): ValidationErrors | null {
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
  
    if (!emailPattern.test(c.value)) {
      return { 'invalidEmail': true };
    }
  
    return null;
  }
}