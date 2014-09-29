using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LPOA.Model
{
    public class NorthwindContext : DbContext
    {
        public NorthwindContext() : base("name=NorthwindContext") { }

        public DbSet<Leave> Leaves { get; set; }

       public DbSet<WorkFlowEntity> WorkFlows { get; set; }

       public void AddEntity(Leave entity)
       {
           Leaves.Add(entity);
           base.SaveChanges();
       }

        //public Leave GetById(long id)
        //{
        //    return Leaves.Where(p => p.Id == id).FirstOrDefault();

        //}

        
    }
    //public class NorthwindContext : DbContext 
    //{
    //    public DbSet<Leave> Leaves { get; set; }

    //    public void AddEntity(Leave entity)
    //    {
    //        Leaves.Add(entity);
    //        base.SaveChanges();
    //    }

    //    public DbSet<WorkFlowEntity> WorkFlows { get; set; }

    //    public void AddEntity(WorkFlowEntity entity)
    //    {
    //        WorkFlows.Add(entity);
    //        base.SaveChanges();
    //    }
    //}
}
