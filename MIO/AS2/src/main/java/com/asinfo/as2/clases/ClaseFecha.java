/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   5:    */ import java.util.Date;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.persistence.Transient;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="tmp_clase_fecha")
/*  17:    */ public class ClaseFecha
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="tmp_clase_fecha", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_clase_fecha")
/*  24:    */   @Column(name="id_tmp_clase_fecha")
/*  25:    */   private Integer idClaseFecha;
/*  26:    */   @Transient
/*  27:    */   private Date fecha;
/*  28:    */   @Transient
/*  29:    */   private String fechaLetras;
/*  30:    */   
/*  31:    */   public ClaseFecha() {}
/*  32:    */   
/*  33:    */   public ClaseFecha(Date fecha)
/*  34:    */   {
/*  35: 69 */     this.fecha = fecha;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Integer getIdClaseFecha()
/*  39:    */   {
/*  40: 78 */     return this.idClaseFecha;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setIdClaseFecha(Integer idClaseFecha)
/*  44:    */   {
/*  45: 88 */     this.idClaseFecha = idClaseFecha;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Date getFecha()
/*  49:    */   {
/*  50: 97 */     return this.fecha;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setFecha(Date fecha)
/*  54:    */   {
/*  55:107 */     this.fecha = fecha;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getFechaLetras()
/*  59:    */   {
/*  60:111 */     this.fechaLetras = FuncionesUtiles.convertidorFechaALetrasHoras(this.fecha);
/*  61:112 */     return this.fechaLetras;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setFechaLetras(String fechaLetras)
/*  65:    */   {
/*  66:116 */     this.fechaLetras = fechaLetras;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getId()
/*  70:    */   {
/*  71:121 */     return getIdClaseFecha().intValue();
/*  72:    */   }
/*  73:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.ClaseFecha
 * JD-Core Version:    0.7.0.1
 */