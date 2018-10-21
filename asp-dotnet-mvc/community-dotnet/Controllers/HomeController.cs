using System;
using System.Threading.Tasks;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;

using community_dotnet.Models;
using community_dotnet.ViewModels;

namespace community_dotnet.Controllers
{
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

            View().ViewName = "~/Views/Home/Index.cshtml";
            return View();
        }

        [HttpGet]
        public ViewResult Inbox()
        {   
            View().ViewName = "~/Views/Home/Inbox.cshtml";
            var inboxViewModel = new InboxViewModel();

            if(inboxViewModel.SelectedSender != null){
                Console.WriteLine("Read page VIEW -> " + inboxViewModel.SelectedSender);
                return View(inboxViewModel);
            }

            if (true) 
            {
                inboxViewModel.AllSenderViewModel = model.GetAllInboxSenders("sanyang@kth.se");
                inboxViewModel.NumberOfMessages = model.GetInbox("sanyang@kth.se").Count;
                inboxViewModel.NumberOfUnreadMessage = model.GetInbox("sanyang@kth.se").FindAll(m => m.IsSeen == false).Count;
                

                return View(inboxViewModel);
            }

        }

        [HttpGet]
        public ActionResult InboxSender(string id) 
        {

            Console.WriteLine("Selected user is " + id + ".");

            if (id != null)
            {
                InboxViewModel inboxViewModel = new InboxViewModel();

                Console.WriteLine(id + " is now being prepared.. ");
                View().ViewName = "~/Views/Home/InboxSender.cshtml";
                inboxViewModel.SenderMessages = model.GetInbox("sanyang@kth.se", id);
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
            Console.WriteLine("Preparing for deletetion of message " + id + "..");
            if(model.DeleteMessage(id)){
                return RedirectToAction("Inbox");
            }

            return RedirectToAction("Index");
        }

        [HttpGet]
        public ActionResult Send()
        {
            View().ViewName = "~/Views/Home/Send.cshtml";
            SendViewModel sendViewModel = new SendViewModel();

            List<string> list = new List<string>();

            foreach(var u in model.GetListOfAllUser()){
                list.Add(u.Username);
            }
            sendViewModel.ListOfContacts = list;

            return View(sendViewModel);
        }

        [HttpPost]
        public ActionResult Send(SendViewModel sendViewModel)
        {
            View().ViewName = "~/Views/Home/Send.cshtml";
           
            var message = new Message
            {
                TextMessage = sendViewModel.Message,
                ToUser = new User { Username = sendViewModel.To },
                FromUser = new User {Username = sendViewModel.From }
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
            return View(_svm);
        }


        public ActionResult Privacy()
        {
            return View();
        }


        //[ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        //public ActionResult Error()
        //{
        //    return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        //}

    }
}
