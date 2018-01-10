/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Rubro;
/*  4:   */ import com.asinfo.as2.entities.TipoSubsidio;
/*  5:   */ import javax.ejb.Stateless;
/*  6:   */ 
/*  7:   */ @Stateless
/*  8:   */ public class TipoSubsidioDao
/*  9:   */   extends AbstractDaoAS2<TipoSubsidio>
/* 10:   */ {
/* 11:   */   public TipoSubsidioDao()
/* 12:   */   {
/* 13:31 */     super(TipoSubsidio.class);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public TipoSubsidio cargarDetalle(int idTipoSubsidio)
/* 17:   */   {
/* 18:41 */     TipoSubsidio tipoSubsidio = new TipoSubsidio();
/* 19:42 */     tipoSubsidio = (TipoSubsidio)buscarPorId(Integer.valueOf(idTipoSubsidio));
/* 20:43 */     tipoSubsidio.getIdTipoSubsidio();
/* 21:44 */     if (tipoSubsidio.getRubro() != null) {
/* 22:45 */       tipoSubsidio.getRubro().getIdRubro();
/* 23:   */     }
/* 24:47 */     return tipoSubsidio;
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.TipoSubsidioDao
 * JD-Core Version:    0.7.0.1
 */