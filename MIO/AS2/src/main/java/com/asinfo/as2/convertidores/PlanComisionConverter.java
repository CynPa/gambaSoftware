/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.PlanComision;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=PlanComision.class)
/*  7:   */ public class PlanComisionConverter
/*  8:   */   extends EntidadBaseConverter<PlanComision>
/*  9:   */ {
/* 10:   */   public PlanComisionConverter()
/* 11:   */   {
/* 12:30 */     super("om.asinfo.as2.entities.PlanComision");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.PlanComisionConverter
 * JD-Core Version:    0.7.0.1
 */