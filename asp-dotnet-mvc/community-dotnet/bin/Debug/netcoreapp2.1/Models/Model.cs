using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using System.Linq;

namespace community_dotnet.Models
{

    public class Model
    {

        readonly Database dbContext;

        public Model()
        {
            dbContext = new Database();
        }

        public bool SendMessage(Message m){
            
            Console.WriteLine("Message to be sent -> " + m);
            Console.WriteLine("Is database active: " + dbContext != null);

            using (var db = dbContext)
            {
                try
                {
                    var frUsr = new UserPoco();
                    frUsr.Username = m.FromUser.Username;
                    frUsr.Password = "null";

                    var toUsr = new UserPoco();
                    toUsr.Username = m.ToUser.Username;
                    toUsr.Password = "null";

                    db.Users.Attach(toUsr);
                    db.Users.Attach(frUsr);

                    var message = new MessagePoco();
                    message.FromUser = frUsr;
                    message.ToUser = toUsr;
                    message.Timestamp = DateTime.Now + "";
                    message.TextMessage = m.TextMessage;

                    Console.WriteLine(message.FromUser + ", " + message.ToUser + ", " + message.TextMessage);

                    db.Messages.Add(message);
                    
                    db.SaveChanges();

                    // increase number of message recieved by 1 in the toUser

                    var query = (from u in db.Users where u.Username == m.ToUser.Username select u).First();

                    query.NumberOfMessageSent = query.NumberOfMessageSent + 1;

                    db.SaveChanges();

                    Console.WriteLine("\n\nMessage sent!\n\n");
                    return true;
                }
                catch (Exception e)
                {
                    Console.WriteLine("Exception at sending message -> " + " - " + e.Message + " \n\n " + e.InnerException + "\n\n" + e.StackTrace);
                }
            }

            return false;
        }

        public Message GetMessage(int id) {

            Console.WriteLine("Retrieving message with id " + id + ".");

            try
            {
                var db = dbContext;
                var message = new Message();
                var fromUser = new User();
                var toUser = new User();

                var query = (from m in db.Messages where m.MessageId == id select m).Include(f => f.FromUser).Include(t => t.ToUser).First();

                Console.WriteLine("\n\nStarting getting the message..");

                var item = query;

                fromUser.Username = item.FromUser.Username;
                toUser.Username = item.ToUser.Username;

                message = new Message
                {
                    MessageId = item.MessageId,
                    FromUser = fromUser,
                    ToUser = toUser,
                    IsSeen = item.IsSeen,
                    TextMessage = item.TextMessage,
                    Timestamp = item.Timestamp
                };



                Console.WriteLine("..Done");

                return message;
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception occurred getting inbox -> " + e.StackTrace + "\n More information: \n" + e.Message + "\n" + e.InnerException);
            }

            return null;
        }

        public List<Message> GetInbox(string recv_username)
        {
            List<Message> msglist = new List<Message>();

            try
            {
                var db = dbContext;
                var message = new Message();
                var query = (from m in db.Messages where m.ToUser.Username == recv_username select m)
                    .Include(f => f.FromUser).Include(t => t.ToUser).ToList();


                Console.WriteLine("\n\nStarting getting inbox..");

                foreach (var item in query)
                {
                    var fromUser = new User();
                    fromUser.Username = item.FromUser.Username;

                    var toUser = new User();
                    toUser.Username = item.ToUser.Username;

                    message = new Message
                    {
                        FromUser = fromUser,
                        ToUser = toUser,
                        IsSeen = item.IsSeen,
                        TextMessage = item.TextMessage,
                        Timestamp = item.Timestamp
                    };



                    msglist.Add(message);
                }

                Console.WriteLine("..Done");

                return msglist;
            } catch(Exception e){
                Console.WriteLine("Exception occurred getting inbox -> " + e.StackTrace + "\n More information: \n" + e.Message);
            }
            
            return null;
        }

        public List<Message> GetInbox(string recv_username, string sender_username)
        {
            List<Message> msglist = new List<Message>();

            try
            {
                var db = dbContext;
                var message = new Message();
                var query = (from m in db.Messages where 
                             m.ToUser.Username == recv_username 
                             && m.FromUser.Username == sender_username  select m)
                    .Include(f => f.FromUser).Include(t => t.ToUser).ToList();

                //foreach ( var i in query){
                //    if(i.FromUser != null) 
                //        Console.WriteLine("MESSAGE -> FROM: " + i.FromUser.Username + ", TO: " + i.ToUser);
                //}

                Console.WriteLine("\n\nStarting getting inbox..");

                foreach (var item in query)
                {
                    var fromUser = new User();
                    fromUser.Username = item.FromUser.Username;

                    var toUser = new User();
                    toUser.Username = item.ToUser.Username;

                    message = new Message
                    {
                        MessageId = item.MessageId,
                        FromUser = fromUser,
                        ToUser = toUser,
                        IsSeen = item.IsSeen,
                        TextMessage = item.TextMessage,
                        Timestamp = item.Timestamp
                    };



                    msglist.Add(message);
                }

                Console.WriteLine("..Done");

                return msglist;
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception occurred getting inbox -> " + e.StackTrace + "\n More information: \n" + e.Message);
            }

            return null;
        }

        public List<User> GetAllInboxSenders(string username) 
        {

            List<User> senderList = new List<User>();

            User user = new User
            {
                Username = username
            };

            var db = dbContext;

            var message = new Message();
            var query = (from m in db.Messages where m.ToUser.Username == user.Username select m.FromUser).Distinct();

            foreach (var item in query)
            {
                var fromUser = new User
                {
                    Username = item.Username
                };
                senderList.Add(fromUser);
            }

            return senderList;
        }

        public int GetNumberOfUnreadMessage(string recv_username)
        {
            using (var db = dbContext)
            {
                try
                {
                    var message = new Message();
                    var query = (from m in db.Messages where m.IsSeen == false 
                                 && m.ToUser.Username == recv_username select m).Count();

                    return query;
                }
                catch (Exception e){
                    Console.WriteLine("Exception occurred -> " + e.StackTrace);
                }
            }

            return -1;
        }

        public List<Message> GetSentMessages(string username)
        {
            List<Message> msglist = new List<Message>();
            List<User> usrList = new List<User>();

            // fix a user proxy as 'binding agent'
            User user = new User
            {
                Username = username
            };

            var db = dbContext;

            var message = new Message();
            var query = (from m in db.Messages where m.ToUser.Username == user.Username select m)
                .Include(f => f.FromUser).Include(t => t.ToUser);

            foreach (var item in query)
            {
                var fromUser = new User();
                fromUser.Username = item.FromUser.Username;
                var toUser = new User();
                toUser.Username = item.ToUser.Username;

                message = new Message
                {
                    FromUser = fromUser,
                    ToUser = toUser,
                    IsSeen = item.IsSeen,
                    TextMessage = item.TextMessage,
                    Timestamp = item.Timestamp
                };

                msglist.Add(message);
            }

            return msglist;
        }

        public List<User> GetListOfAllUser()
        {
            List<User> list = new List<User>();

            using (var db = new Database())
            {

                var query = from u in db.Users select u;
                foreach (var item in query)
                {
                    var user = new User
                    {
                        Username = item.Username,
                        NumberOfLoginsInMonth = item.NumberOfLoginsInMonth,
                        NumberOfMessageSent = item.NumberOfMessageSent,
                        LatestLogin = item.LatestLogin,
                        NumberOfUnreadMessages = item.NumberOfUnreadMessages
                    };
                    list.Add(user);

                }
            }
            return list;
        }

        public bool SetMessageIsSeen(int id){

            var db = dbContext;

            var query = (from m in db.Messages where m.MessageId == id select m).First();

            query.IsSeen = true;

            try 
            {
                db.SaveChanges();
                Console.WriteLine("Message with id " + id + " have been read.");
                return true;
            } 
            catch(Exception e)
            {
                Console.WriteLine("Exception occurred while updating message -> " 
                                  + e.StackTrace + "\nMore information -> " + e.Message);
            }
            return false;
        }

        public bool DeleteMessage(int id){

            var db = dbContext;

            try
            {
                var query = (from m in db.Messages where m.MessageId == id select m).First();
                if(query == null) {
                    Console.WriteLine("Error deleting the message, transaction aborted. Exception is thrown.");
                    throw new Exception();
                }
                db.Messages.Remove(query);
                db.SaveChanges();
                Console.WriteLine("Message with id " + id + " have been deleted.");
                return true;
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception occurred while updating message -> "
                                  + e.StackTrace + "\nMore information -> " + e.Message);
            }

            return false;
        }

    }

}
