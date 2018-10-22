using System.Collections.Generic;
using System.Security.Claims;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Authentication;
using CommunityDotnet.ViewModels;
using CommunityDotnet.Models;

namespace CommunityDotnet.Controllers
{
    public class AccountController : Controller
    {
        [HttpGet]
        public ActionResult Login()
        {
            View().ViewName = "~/Views/Home/Login.cshtml";
            return View();
        }

        [HttpPost]
        public async Task<ActionResult> Login(LoginViewModel loginViewModel)
        {
            ViewData["User"] = null;
            View().ViewName = "~/Views/Home/Login.cshtml";
            var user = new User
            {
                Username = loginViewModel.Username,
                Password = loginViewModel.Password
            };

            bool isLoggedIn = new Model().TryLogin(user);

            if (isLoggedIn)
            {
                var claims = new List<Claim> {
                    new Claim(ClaimTypes.Name, user.Username)
                };

                var userIdentity = new ClaimsIdentity(claims, "login");
                ClaimsPrincipal principal = new ClaimsPrincipal(userIdentity);

                await HttpContext.SignInAsync(CookieAuthenticationDefaults.AuthenticationScheme, principal);

                return RedirectToAction("Inbox", "Home");
            }
            else
            {
                ViewData["FailedLogin"] = "Login failed, try again";
                return View();
            }

        }

        [HttpGet]
        public async Task<ActionResult> Logout()
        {
            ViewData["User"] = null;
            await HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
            return RedirectToAction("Login");
        }

        [HttpGet]
        public ActionResult Register()
        {
            ViewData["User"] = null;
            View().ViewName = "~/Views/Home/Register.cshtml";
            return View();
        }

        [HttpPost]
        public ActionResult Register(RegisterViewModel registerViewModel)
        {
            View().ViewName = "~/Views/Home/Register.cshtml";
            var user = new User
            {
                Username = registerViewModel.Username,
                Password = registerViewModel.Password
            };

            bool isRegistered = new Model().TryRegister(user);

            if (isRegistered)
            {
                return RedirectToAction("Login");
            }
            else
            {
                ViewData["FailedRegister"] = "Register failed, try again";
                return View();
            }

        }
    }
}
