 function checkPassword(form) {
                password1 = form.password.value;
                password2 = form.passwordcheck.value;
                message = form.message.value
  
                if (password1 != password2) {
                    alert (message)
                    return false;
                }
  
                else{
                    return true;
                }
            }