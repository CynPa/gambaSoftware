/*   1:    */ package com.asinfo.as2.entities.nomina.asistencia;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.Date;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.persistence.Temporal;
/*  14:    */ import javax.persistence.TemporalType;
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="dia_festivo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "fecha"})})
/*  19:    */ public class DiaFestivo
/*  20:    */   extends EntidadBase
/*  21:    */   implements Serializable
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = -6054909171994634772L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="dia_festivo", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="dia_festivo")
/*  27:    */   @Column(name="id_dia_festivo")
/*  28:    */   private int idDiaFestivo;
/*  29:    */   @Column(name="id_organizacion")
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal")
/*  32:    */   private int idSucursal;
/*  33:    */   @Column(name="descripcion", length=200, nullable=true)
/*  34:    */   @Size(max=200)
/*  35:    */   private String descripcion;
/*  36:    */   @Column(name="predeterminado", nullable=false)
/*  37:    */   private boolean predeterminado;
/*  38:    */   @Column(name="activo", nullable=false)
/*  39:    */   private boolean activo;
/*  40:    */   @Column(name="indicador_repetir", nullable=false)
/*  41:    */   private boolean indicadorRepetir;
/*  42:    */   @Temporal(TemporalType.DATE)
/*  43:    */   @Column(name="fecha", nullable=true)
/*  44:    */   private Date fecha;
/*  45:    */   
/*  46:    */   public int getId()
/*  47:    */   {
/*  48: 89 */     return this.idDiaFestivo;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getIdDiaFestivo()
/*  52:    */   {
/*  53: 93 */     return this.idDiaFestivo;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setIdDiaFestivo(int idDiaFestivo)
/*  57:    */   {
/*  58: 97 */     this.idDiaFestivo = idDiaFestivo;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getIdOrganizacion()
/*  62:    */   {
/*  63:101 */     return this.idOrganizacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setIdOrganizacion(int idOrganizacion)
/*  67:    */   {
/*  68:105 */     this.idOrganizacion = idOrganizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getIdSucursal()
/*  72:    */   {
/*  73:109 */     return this.idSucursal;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIdSucursal(int idSucursal)
/*  77:    */   {
/*  78:113 */     this.idSucursal = idSucursal;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getDescripcion()
/*  82:    */   {
/*  83:117 */     return this.descripcion;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setDescripcion(String descripcion)
/*  87:    */   {
/*  88:121 */     this.descripcion = descripcion;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean isPredeterminado()
/*  92:    */   {
/*  93:125 */     return this.predeterminado;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setPredeterminado(boolean predeterminado)
/*  97:    */   {
/*  98:129 */     this.predeterminado = predeterminado;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean isActivo()
/* 102:    */   {
/* 103:133 */     return this.activo;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setActivo(boolean activo)
/* 107:    */   {
/* 108:137 */     this.activo = activo;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Date getFecha()
/* 112:    */   {
/* 113:141 */     return this.fecha;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setFecha(Date fecha)
/* 117:    */   {
/* 118:145 */     this.fecha = fecha;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isIndicadorRepetir()
/* 122:    */   {
/* 123:149 */     return this.indicadorRepetir;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setIndicadorRepetir(boolean indicadorRepetir)
/* 127:    */   {
/* 128:153 */     this.indicadorRepetir = indicadorRepetir;
/* 129:    */   }
/* 130:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.nomina.asistencia.DiaFestivo
 * JD-Core Version:    0.7.0.1
 */