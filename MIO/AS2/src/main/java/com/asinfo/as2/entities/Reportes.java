/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.OneToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.persistence.Temporal;
/*  16:    */ import javax.persistence.TemporalType;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="reportes")
/*  22:    */ public class Reportes
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="reporte", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="reporte")
/*  29:    */   @Column(name="id_reporte")
/*  30:    */   private int idReporte;
/*  31:    */   @Column(name="nombre", nullable=false, length=50)
/*  32:    */   @NotNull
/*  33:    */   @Size(max=50)
/*  34:    */   private String nombre;
/*  35:    */   @Column(name="fecha", nullable=false)
/*  36:    */   @Temporal(TemporalType.DATE)
/*  37:    */   @NotNull
/*  38:    */   private Date fecha;
/*  39:    */   @Column(name="version", nullable=false, length=10)
/*  40:    */   @NotNull
/*  41:    */   @Size(max=10)
/*  42:    */   private String version;
/*  43:    */   @OneToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_sistema")
/*  45:    */   private EntidadSistema sistema;
/*  46:    */   @Column(name="organizacion", nullable=false, length=100)
/*  47:    */   @NotNull
/*  48:    */   @Size(max=100)
/*  49:    */   private String organizacion;
/*  50:    */   @Column(name="actualizado", nullable=true)
/*  51:    */   private boolean actualizado;
/*  52:    */   
/*  53:    */   public int getIdReporte()
/*  54:    */   {
/*  55: 89 */     return this.idReporte;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setIdReporte(int idReporte)
/*  59:    */   {
/*  60: 93 */     this.idReporte = idReporte;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getNombre()
/*  64:    */   {
/*  65: 97 */     return this.nombre;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Date getFecha()
/*  69:    */   {
/*  70:101 */     return this.fecha;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getVersion()
/*  74:    */   {
/*  75:105 */     return this.version;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public EntidadSistema getSistema()
/*  79:    */   {
/*  80:109 */     return this.sistema;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean isActualizado()
/*  84:    */   {
/*  85:113 */     return this.actualizado;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setNombre(String nombre)
/*  89:    */   {
/*  90:117 */     this.nombre = nombre;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setFecha(Date fecha)
/*  94:    */   {
/*  95:121 */     this.fecha = fecha;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setVersion(String version)
/*  99:    */   {
/* 100:125 */     this.version = version;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setSistema(EntidadSistema sistema)
/* 104:    */   {
/* 105:129 */     this.sistema = sistema;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setActualizado(boolean actualizado)
/* 109:    */   {
/* 110:133 */     this.actualizado = actualizado;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getOrganizacion()
/* 114:    */   {
/* 115:137 */     return this.organizacion;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setOrganizacion(String organizacion)
/* 119:    */   {
/* 120:141 */     this.organizacion = organizacion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public int getId()
/* 124:    */   {
/* 125:147 */     return 0;
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Reportes
 * JD-Core Version:    0.7.0.1
 */