/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Sector;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Sector.class)
/*  7:   */ public class SectorConverter
/*  8:   */   extends EntidadBaseConverter<Sector>
/*  9:   */ {
/* 10:   */   public SectorConverter()
/* 11:   */   {
/* 12:27 */     super("com.asinfo.as2.entities.Sector");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.SectorConverter
 * JD-Core Version:    0.7.0.1
 */