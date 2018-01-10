/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.produccion.PlanMaestroProduccion;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=PlanMaestroProduccion.class)
/*  7:   */ public class PlanMaestroProduccionConverter
/*  8:   */   extends EntidadBaseConverter<PlanMaestroProduccion>
/*  9:   */ {
/* 10:   */   public PlanMaestroProduccionConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.produccion.PlanMaestroProduccion");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.PlanMaestroProduccionConverter
 * JD-Core Version:    0.7.0.1
 */