/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.OneToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="reportes_personalizados")
/*  19:    */ public class ReportesPersonalizados
/*  20:    */   extends EntidadBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="reporte_personalizado", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="reporte_personalizado")
/*  26:    */   @Column(name="id_reporte_personalizado")
/*  27:    */   private int idReportePersonalizado;
/*  28:    */   @Column(name="nombre", nullable=false, length=50)
/*  29:    */   @NotNull
/*  30:    */   @Size(max=50)
/*  31:    */   private String nombre;
/*  32:    */   @Column(name="organizacion", nullable=false, length=100)
/*  33:    */   @NotNull
/*  34:    */   @Size(max=100)
/*  35:    */   private String organizacion;
/*  36:    */   @OneToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_sistema")
/*  38:    */   private EntidadSistema sistema;
/*  39:    */   
/*  40:    */   public int getIdReportePersonalizado()
/*  41:    */   {
/*  42: 72 */     return this.idReportePersonalizado;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setIdReportePersonalizado(int idReportePersonalizado)
/*  46:    */   {
/*  47: 76 */     this.idReportePersonalizado = idReportePersonalizado;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getNombre()
/*  51:    */   {
/*  52: 80 */     return this.nombre;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public EntidadSistema getSistema()
/*  56:    */   {
/*  57: 84 */     return this.sistema;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setNombre(String nombre)
/*  61:    */   {
/*  62: 88 */     this.nombre = nombre;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setSistema(EntidadSistema sistema)
/*  66:    */   {
/*  67: 92 */     this.sistema = sistema;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getOrganizacion()
/*  71:    */   {
/*  72: 96 */     return this.organizacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setOrganizacion(String organizacion)
/*  76:    */   {
/*  77:100 */     this.organizacion = organizacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getId()
/*  81:    */   {
/*  82:106 */     return 0;
/*  83:    */   }
/*  84:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ReportesPersonalizados
 * JD-Core Version:    0.7.0.1
 */