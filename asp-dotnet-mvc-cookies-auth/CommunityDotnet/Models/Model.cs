using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using System.Linq;
using CommunityDotnet.Data;

namespace CommunityDotnet.Models
{

    public class Model
    {

        readonly CommunityDbContext dbContext;

        public Model()
        {
            dbContext = new CommunityDbContext();
        }

        public bool TryLogin(User user)
        {
            try
            {
                Console.WriteLine("Preparing User("+ user.Username + "," + user.Password +") for login..");

                using (var db = dbContext)
                {

                    var query = (from u in db.Users
                                 where u.Username == user.Username
                                 select u);

                    //var query = db.Users
                    //.Where(u => u.Username == user.Username)
                    //    .Where(u => u.Password == user.Password)
                    //.Select(s => s)
                    //.Include(l => l.LatestLogin)
                    //    .Include(n => n.NumberOfLoginsInMonth)
                    //.ToList();

                    Console.WriteLine("Users found: " + query.Count());
                    if (query.Any())
                    {

                        Console.WriteLine("Query: " + query);

                        var instant = query.Single();

                        Console.WriteLine("Linq entity:" + instant.Username + "," + instant.Password + "," + instant.LatestLogin);

                        if(instant.Password != user.Password) 
                        {
                            return false;
                        }
                        if (instant.LatestLogin != null)
                        {
                            if (instant.LatestLogin.Substring(0, 6).Equals(DateTime.Now.ToString().Substring(0, 6)))
                            {
                                instant.NumberOfLoginsInMonth = instant.NumberOfLoginsInMonth + 1;
                            }
                            else
                            {
                                instant.NumberOfLoginsInMonth = 1;
                            }
                            instant.LatestLogin = DateTime.Now.ToString();
                        }
                        else
                        {
                            instant.LatestLogin = DateTime.Now.ToString(); instant.NumberOfLoginsInMonth = 1;
                        }
                        db.SaveChanges();

                        Console.WriteLine("..User succesful logged in.");
                        return true;
                    }
                }

            }
            catch (Exception e)
            {
                Console.WriteLine("Exception occurred trying login -> " + e.StackTrace + "\n More information: \n" + e.Message + "\n" + e.InnerException);
            }

            Console.WriteLine("..User login failed.");
            return false;

        }

        public bool TryRegister(User user) 
        {
            try
            {
                using (var db = dbContext)
                {
                    Console.WriteLine("Preparing User for registration..");
                    var registerUser = new UserPoco();

                    registerUser.Username = user.Username;
                    registerUser.Password = user.Password;

                    Console.WriteLine("\tTrying to register '" + registerUser.Username + "'...");
                    db.Users.Add(registerUser);

                    db.SaveChanges();

                    Console.WriteLine("..User registration completed.");
                    return true;

                }            
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception at registraring User -> " + " - " + e.Message + " \n\n " + e.InnerException + "\n\n" + e.StackTrace);
            }

            Console.WriteLine("..User registration failed.");
            return false;
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

                    Console.WriteLine(message.FromUser.Username + ", " + message.ToUser.Username + ", " + message.TextMessage);

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

        public User GetUser(string id)
        {

            Console.WriteLine("Retrieving User with id " + id + ".");

            try
            {
                var db = dbContext;


                var query = (from u in db.Users where u.Username == id select u).First();

                Console.WriteLine("\n\nStarting getting the User..");

                var user = new User
                {
                    Username = query.Username,
                    LatestLogin = query.LatestLogin,
                    NumberOfMessageSent = query.NumberOfMessageSent,
                    NumberOfLoginsInMonth = query.NumberOfLoginsInMonth,
                    NumberOfUnreadMessages = query.NumberOfUnreadMessages
                };

                Console.WriteLine("..Done");

                return user;
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception occurred getting User -> " + e.StackTrace + "\n More information: \n" + e.Message + "\n" + e.InnerException);
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
            Console.WriteLine("Going to load all " + username + "'s senders..");
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
                Console.WriteLine("\tUser '" + fromUser.Username + "' is added.");
                senderList.Add(fromUser);
            }

            Console.WriteLine("..loading.");
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
            Console.WriteLine("Preparing to add Users to the list..");
            List<User> list = new List<User>();

            using (var db = new CommunityDbContext())
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
                    Console.WriteLine("\tUser '" + user.Username + "' is added to the list.");
                }
            }

            Console.WriteLine("..Users are succesful added to the list.");
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
