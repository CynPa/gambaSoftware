/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.ImpuestoRentaSRI;
/*  4:   */ import java.util.List;
/*  5:   */ import javax.ejb.Stateless;
/*  6:   */ import javax.persistence.EntityManager;
/*  7:   */ import javax.persistence.Query;
/*  8:   */ 
/*  9:   */ @Stateless
/* 10:   */ public class ImpuestoRentaSRIDao
/* 11:   */   extends AbstractDaoAS2<ImpuestoRentaSRI>
/* 12:   */ {
/* 13:   */   public ImpuestoRentaSRIDao()
/* 14:   */   {
/* 15:37 */     super(ImpuestoRentaSRI.class);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public ImpuestoRentaSRI cargarDetalle(int idImpuestoRentaSRI)
/* 19:   */   {
/* 20:47 */     return null;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public List<ImpuestoRentaSRI> obtenerTablaPorAnio(int anio, int idOrganizacion)
/* 24:   */   {
/* 25:58 */     Query query = this.em.createQuery(" SELECT ir FROM ImpuestoRentaSRI ir WHERE ir.anio = :anio AND ir.idOrganizacion = :idOrganizacion ORDER BY ir.fraccionBasica, ir.desde");
/* 26:   */     
/* 27:   */ 
/* 28:61 */     query.setParameter("anio", Integer.valueOf(anio));
/* 29:62 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 30:63 */     return query.getResultList();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<ImpuestoRentaSRI> verificarCantidades(int idOrganizacion, int anio)
/* 34:   */   {
/* 35:69 */     Query query = this.em.createQuery("SELECT i FROM ImpuestoRentaSRI i WHERE i.idOrganizacion =:idOrganizacion and i.anio = :anio and i.activo = true ");
/* 36:   */     
/* 37:   */ 
/* 38:72 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 39:73 */     query.setParameter("anio", Integer.valueOf(anio));
/* 40:   */     
/* 41:75 */     return query.getResultList();
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ImpuestoRentaSRIDao
 * JD-Core Version:    0.7.0.1
 */