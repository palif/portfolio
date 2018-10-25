using System;
using Microsoft.AspNetCore.Authorization;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Http;

using CommunityDotnet.Models;
using CommunityDotnet.ViewModels;

namespace CommunityDotnet.Controllers
{
    [Authorize]
    public class HomeController : Controller
    {
        private readonly Model model;

        public HomeController()
        {
            this.model = new Model();
        }

        [HttpGet]
        public ActionResult Index()
        {
            ViewData["User"] = HttpContext.User?.Identity?.Name;
            View().ViewName = "~/Views/Home/Index.cshtml";
            var mail = HttpContext.User?.Identity?.Name;
            ViewData["User"] = mail;
            User user = model.GetUser(mail);
            ViewData["LatestLogin"] = user.LatestLogin;
            ViewData["NrOfLoginsMonth"] = user.NumberOfLoginsInMonth;
            ViewData["NrOfMessagesSent"] = user.NumberOfMessageSent;
            ViewData["NrOfMessagesRecv"] = model.GetInbox(mail).Count;
            ViewData["NrOfUnreadMessages"] = model.GetInbox(mail).FindAll(m => m.IsSeen == false).Count;

            return View();
        }

        [HttpGet]
        public ActionResult Inbox()
        {
            ViewData["User"] = HttpContext.User?.Identity?.Name;
            var userMail = HttpContext.User?.Identity?.Name;
            View().ViewName = "~/Views/Home/Inbox.cshtml";

            var inboxViewModel = new InboxViewModel
            {
                AllSenderViewModel = model.GetAllInboxSenders(userMail),
                NumberOfMessages = model.GetInbox(userMail).Count,
                NumberOfUnreadMessage = model.GetInbox(userMail).FindAll(m => m.IsSeen == false).Count
            };

            return View(inboxViewModel);


        }

        [HttpGet]
        public ActionResult InboxSender(string id) 
        {
            ViewData["User"] = HttpContext.User?.Identity?.Name;
            var userMail = HttpContext.User?.Identity?.Name;
            Console.WriteLine("Selected user is " + id + ".");

            if (id != null)
            {
                InboxViewModel inboxViewModel = new InboxViewModel();

                Console.WriteLine(id + " is now being prepared.. ");
                View().ViewName = "~/Views/Home/InboxSender.cshtml";
                inboxViewModel.SenderMessages = model.GetInbox(userMail, id);
                inboxViewModel.SelectedSender = id;
                foreach(var m in inboxViewModel.SenderMessages) {
                    Console.WriteLine(id + " message -> " + m.TextMessage);
                }

                Console.WriteLine(".." + id + " is now ready.");
                return View(inboxViewModel);
            }

            Console.WriteLine("Selected user is nulled, HTTPRequest is bad");

            return View();
        }

        [HttpGet]
        public ActionResult InboxMessage(int id)
        {
            ViewData["User"] = HttpContext.User?.Identity?.Name;
            Console.WriteLine("Selected message is " + id + ".");

            if (id != -1)
            {
                InboxViewModel inboxViewModel = new InboxViewModel();
                model.SetMessageIsSeen(id);
                Console.WriteLine(id + " is now being prepared.. ");
                View().ViewName = "~/Views/Home/InboxSender.cshtml";
                inboxViewModel.SelectedMessage = model.GetMessage(id);

                Console.WriteLine(id + " message -> " + inboxViewModel.SelectedMessage);

                Console.WriteLine(".." + id + " is now ready.");
                return View(inboxViewModel);
            }

            Console.WriteLine("Selected user is nulled, HTTPRequest is bad");

            return View();
        }

        [HttpPost, ActionName("InboxDelete")]
        public ActionResult InboxDelete(int id)
        {
            ViewData["User"] = HttpContext.User?.Identity?.Name;
            Console.WriteLine("Preparing for deletetion of message " + id + "..");
            if(model.DeleteMessage(id)){
                return RedirectToAction("Inbox");
            }

            return RedirectToAction("Index");
        }

        [HttpGet]
        public ActionResult Send()
        {
            ViewData["User"] = HttpContext.User?.Identity?.Name;
            View().ViewName = "~/Views/Home/Send.cshtml";
            SendViewModel sendViewModel = new SendViewModel();

            List<string> list = new List<string>();

            foreach(var u in model.GetListOfAllUser()){
                list.Add(u.Username);
            }
            sendViewModel.ListOfContacts = list;
            sendViewModel.From = HttpContext.User?.Identity?.Name;

            return View(sendViewModel);
        }

        [HttpPost]
        public ActionResult Send(SendViewModel sendViewModel)
        {
            ViewData["User"] = HttpContext.User?.Identity?.Name;
            View().ViewName = "~/Views/Home/Send.cshtml";
           
            var message = new Message
            {
                TextMessage = sendViewModel.Message,
                ToUser = new User { Username = sendViewModel.To },
                FromUser = new User {Username = HttpContext.User?.Identity?.Name}
            };

            Console.WriteLine(message);

            if (model.SendMessage(message)){
                string info = "Sent message complete '" + message + "'";
                ViewData["SentMessageInfo"] = info;

                SendViewModel svm = new SendViewModel();
                List<string> list = new List<string>();

                foreach (var u in model.GetListOfAllUser())
                {
                    list.Add(u.Username);
                }
                svm.ListOfContacts = list;
                svm.From = HttpContext.User?.Identity?.Name;
                return View(svm);
            }

            string _info = "Error sending message -> " + message + ", try again";
            ViewData["SentMessageInfo"] = _info;

            SendViewModel _svm = new SendViewModel();
            List<string> _list = new List<string>();

            foreach (var u in model.GetListOfAllUser())
            {
                _list.Add(u.Username);
            }
            _svm.ListOfContacts = _list;
            _svm.From = HttpContext.User?.Identity?.Name;
            return View(_svm);
        }


        public ActionResult Privacy()
        {
            ViewData["User"] = HttpContext.User?.Identity?.Name;
            return View();
        }


        //[ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        //public ActionResult Error()
        //{
        //    return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        //}

    }
}
