using Nancy;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace LPOA.Web.Module
{
    public class LoginModule: NancyModule
    {
        public LoginModule()
        {
            Get["/login"] = parameters =>
            {
                return View["login"];
            };

            

            Get["/logout"] = parameters =>
            {
                // Called when the user clicks the sign out button in the application. Should
                // perform one of the Logout actions (see below)
                return "Logout";
            };

            Post["/login"] = parameters =>
            {
                // Called when the user submits the contents of the login form. Should
                // validate the user based on the posted form data, and perform one of the
                // Login actions (see below)

                return "Sucess";
            };
        }
    }
}