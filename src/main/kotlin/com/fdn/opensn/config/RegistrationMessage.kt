package com.fdn.opensn.config

class RegistrationMessage {

  companion object {
    const val SUCCESS = "You've been successfully registered"

    const val USERNAME_TAKEN = "Username is already taken"

    const val USERNAME_SHORT = "Username's length can't be less than 3"

    const val PASSWORD_SHORT = "Password's length can't be less than 3"
  }

}
