/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.TableGenerator;
/*  12:    */ import javax.persistence.Temporal;
/*  13:    */ import javax.persistence.TemporalType;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="log_soporte")
/*  19:    */ public class LogSoporte
/*  20:    */   extends EntidadBase
/*  21:    */   implements Serializable
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = -8989608836619793447L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="log_soporte", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="log_soporte")
/*  27:    */   @Column(name="id_log_soporte", unique=true, nullable=false)
/*  28:    */   private int idLogSoporte;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="responsable", nullable=false, length=25)
/*  32:    */   @NotNull
/*  33:    */   @Size(max=25)
/*  34:    */   private String responsable;
/*  35:    */   @Column(name="fecha_auditoria", nullable=true)
/*  36:    */   @Temporal(TemporalType.TIMESTAMP)
/*  37:    */   private Date fechaAuditoria;
/*  38:    */   @Column(name="motivo", nullable=true, length=100)
/*  39:    */   @NotNull
/*  40:    */   @Size(max=100)
/*  41:    */   private String motivo;
/*  42:    */   @Column(name="archivos_eliminados", nullable=true, length=100)
/*  43:    */   @NotNull
/*  44:    */   @Size(max=100)
/*  45:    */   private String archivosEliminados;
/*  46:    */   @Column(name="detalle", nullable=true)
/*  47:    */   @NotNull
/*  48:    */   private String detalle;
/*  49:    */   
/*  50:    */   public String getDetalle()
/*  51:    */   {
/*  52: 60 */     return this.detalle;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setDetalle(String detalle)
/*  56:    */   {
/*  57: 64 */     this.detalle = detalle;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getId()
/*  61:    */   {
/*  62: 69 */     return this.idLogSoporte;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdLogSoporte()
/*  66:    */   {
/*  67: 73 */     return this.idLogSoporte;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdLogSoporte(int idLogSoporte)
/*  71:    */   {
/*  72: 77 */     this.idLogSoporte = idLogSoporte;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getResponsable()
/*  76:    */   {
/*  77: 81 */     return this.responsable;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdOrganizacion()
/*  81:    */   {
/*  82: 85 */     return this.idOrganizacion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdOrganizacion(int idOrganizacion)
/*  86:    */   {
/*  87: 89 */     this.idOrganizacion = idOrganizacion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setResponsable(String responsable)
/*  91:    */   {
/*  92: 93 */     this.responsable = responsable;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Date getFechaAuditoria()
/*  96:    */   {
/*  97: 97 */     return this.fechaAuditoria;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setFechaAuditoria(Date fechaAuditoria)
/* 101:    */   {
/* 102:101 */     this.fechaAuditoria = fechaAuditoria;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getMotivo()
/* 106:    */   {
/* 107:105 */     return this.motivo;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setMotivo(String motivo)
/* 111:    */   {
/* 112:109 */     this.motivo = motivo;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String getArchivosEliminados()
/* 116:    */   {
/* 117:113 */     return this.archivosEliminados;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setArchivosEliminados(String archivosEliminados)
/* 121:    */   {
/* 122:117 */     this.archivosEliminados = archivosEliminados;
/* 123:    */   }
/* 124:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.LogSoporte
 * JD-Core Version:    0.7.0.1
 */