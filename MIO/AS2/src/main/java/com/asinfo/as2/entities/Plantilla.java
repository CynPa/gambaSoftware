/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.NotNull;
/*  11:    */ import javax.validation.constraints.Size;
/*  12:    */ 
/*  13:    */ @Entity
/*  14:    */ @Table(name="plantilla")
/*  15:    */ public class Plantilla
/*  16:    */   extends EntidadBase
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 1L;
/*  19:    */   @Id
/*  20:    */   @TableGenerator(name="plantilla", initialValue=0, allocationSize=50)
/*  21:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="plantilla")
/*  22:    */   @Column(name="id_plantilla")
/*  23:    */   private int idPlantilla;
/*  24:    */   @Column(name="id_organizacion")
/*  25:    */   private int idOrganizacion;
/*  26:    */   @Column(name="codigo", length=50, nullable=false)
/*  27:    */   @NotNull
/*  28:    */   @Size(min=2, max=50)
/*  29:    */   private String codigo;
/*  30:    */   @Column(name="nombre_proceso", length=200, nullable=false)
/*  31:    */   @NotNull
/*  32:    */   @Size(max=200)
/*  33:    */   private String nombreProceso;
/*  34:    */   @Column(name="ruta", length=200, nullable=false)
/*  35:    */   @NotNull
/*  36:    */   @Size(max=200)
/*  37:    */   private String ruta;
/*  38:    */   
/*  39:    */   public int getId()
/*  40:    */   {
/*  41: 63 */     return this.idPlantilla;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Plantilla() {}
/*  45:    */   
/*  46:    */   public Plantilla(int idPlantilla, String codigo, String nombreProceso)
/*  47:    */   {
/*  48: 70 */     this.idPlantilla = idPlantilla;
/*  49: 71 */     this.codigo = codigo;
/*  50: 72 */     this.nombreProceso = nombreProceso;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getIdPlantilla()
/*  54:    */   {
/*  55: 77 */     return this.idPlantilla;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setIdPlantilla(int idPlantilla)
/*  59:    */   {
/*  60: 81 */     this.idPlantilla = idPlantilla;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getIdOrganizacion()
/*  64:    */   {
/*  65: 85 */     return this.idOrganizacion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdOrganizacion(int idOrganizacion)
/*  69:    */   {
/*  70: 89 */     this.idOrganizacion = idOrganizacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getCodigo()
/*  74:    */   {
/*  75: 93 */     return this.codigo;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setCodigo(String codigo)
/*  79:    */   {
/*  80: 97 */     this.codigo = codigo;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getNombreProceso()
/*  84:    */   {
/*  85:101 */     return this.nombreProceso;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setNombreProceso(String nombreProceso)
/*  89:    */   {
/*  90:105 */     this.nombreProceso = nombreProceso;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getRuta()
/*  94:    */   {
/*  95:109 */     return this.ruta;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setRuta(String ruta)
/*  99:    */   {
/* 100:113 */     this.ruta = ruta;
/* 101:    */   }
/* 102:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Plantilla
 * JD-Core Version:    0.7.0.1
 */