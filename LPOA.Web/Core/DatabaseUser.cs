using Nancy;
using Nancy.Authentication.Forms;
using Nancy.Security;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace LPOA.Web
{
    //public interface IUserMapper
    //{
    //    /// <summary>
    //    /// Get the real username from an identifier
    //    /// </summary>
    //    /// <param name="identifier">User identifier</param>
    //    /// <param name="context">The current NancyFx context</param>
    //    /// <returns>Matching populated IUserIdentity object, or empty</returns>
    //    IUserIdentity GetUserFromIdentifier(Guid identifier, NancyContext context);
    //}
    public class DatabaseUser : IUserMapper
    {
        public IUserIdentity GetUserFromIdentifier(Guid identifier, NancyContext context)
        {
            throw new NotImplementedException();
        }
    }

    public class AuthenticatedUser : IUserIdentity
    {
        public string UserName { get; set; }
        public IEnumerable<string> Claims { get; set; }
    }
}