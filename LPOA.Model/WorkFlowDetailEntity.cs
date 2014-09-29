using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LPOA.Model
{
    public class WorkFlowDetailEntity
    {
        public Guid Id { get; set; }
        public Guid FlowId { get; set; }
        public string Worker { get; set; }
        public long FlowResult { get; set; }
        public string Intro { get; set; }
        public DateTime FlowDate { get; set; }

    }

    public class WorkFlowDetailContext : DbContext
    {
        public DbSet<WorkFlowDetailEntity> WorkFlowDetail { get; set; }
        public void AddEntity(WorkFlowDetailEntity entity)
        {
            WorkFlowDetail.Add(entity);
            base.SaveChanges();
        }

        public WorkFlowDetailEntity GetById(Guid id)
        {
            return WorkFlowDetail.Where(p => p.Id == id).FirstOrDefault();

        }
    }
}
