/*   1:    */ package com.asinfo.as2.entities.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
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
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="clave_acceso_pendiente_publicar")
/*  18:    */ public class ClaveAccesoPendientePublicar
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="clave_acceso_pendiente_publicar", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="clave_acceso_pendiente_publicar")
/*  25:    */   @Column(name="id_clave_acceso_pendiente_publicar")
/*  26:    */   private int idClaveAccesoPendientePublicar;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Temporal(TemporalType.DATE)
/*  32:    */   @Column(name="fecha_ultimo_intento", nullable=true)
/*  33:    */   private Date fechaUltimoIntento;
/*  34:    */   @Column(name="cantidad_intentos", nullable=false)
/*  35: 56 */   private int cantidadIntentos = 0;
/*  36:    */   @Column(name="indicador_publicado", nullable=false)
/*  37: 59 */   private boolean indicadorPublicado = false;
/*  38:    */   @Column(name="clave_acceso", nullable=false)
/*  39:    */   private String claveAcceso;
/*  40:    */   @Size(max=500)
/*  41:    */   @Column(name="emails", nullable=true, length=500)
/*  42:    */   private String emails;
/*  43:    */   @Column(name="path_xml", nullable=true)
/*  44:    */   private String pathXML;
/*  45:    */   
/*  46:    */   public int getIdOrganizacion()
/*  47:    */   {
/*  48: 81 */     return this.idOrganizacion;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setIdOrganizacion(int idOrganizacion)
/*  52:    */   {
/*  53: 91 */     this.idOrganizacion = idOrganizacion;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdSucursal()
/*  57:    */   {
/*  58:100 */     return this.idSucursal;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdSucursal(int idSucursal)
/*  62:    */   {
/*  63:110 */     this.idSucursal = idSucursal;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Date getFechaUltimoIntento()
/*  67:    */   {
/*  68:114 */     return this.fechaUltimoIntento;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setFechaUltimoIntento(Date fechaUltimoIntento)
/*  72:    */   {
/*  73:118 */     this.fechaUltimoIntento = fechaUltimoIntento;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getCantidadIntentos()
/*  77:    */   {
/*  78:122 */     return this.cantidadIntentos;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setCantidadIntentos(int cantidadIntentos)
/*  82:    */   {
/*  83:126 */     this.cantidadIntentos = cantidadIntentos;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getIdClaveAccesoPendientePublicar()
/*  87:    */   {
/*  88:130 */     return this.idClaveAccesoPendientePublicar;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIdClaveAccesoPendientePublicar(int idClaveAccesoPendientePublicar)
/*  92:    */   {
/*  93:134 */     this.idClaveAccesoPendientePublicar = idClaveAccesoPendientePublicar;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean isIndicadorPublicado()
/*  97:    */   {
/*  98:138 */     return this.indicadorPublicado;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setIndicadorPublicado(boolean indicadorPublicado)
/* 102:    */   {
/* 103:142 */     this.indicadorPublicado = indicadorPublicado;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getId()
/* 107:    */   {
/* 108:147 */     return this.idClaveAccesoPendientePublicar;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getClaveAcceso()
/* 112:    */   {
/* 113:151 */     return this.claveAcceso;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setClaveAcceso(String claveAcceso)
/* 117:    */   {
/* 118:155 */     this.claveAcceso = claveAcceso;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String getEmails()
/* 122:    */   {
/* 123:159 */     return this.emails;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setEmails(String emails)
/* 127:    */   {
/* 128:163 */     this.emails = emails;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String getPathXML()
/* 132:    */   {
/* 133:167 */     return this.pathXML;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setPathXML(String pathXML)
/* 137:    */   {
/* 138:171 */     this.pathXML = pathXML;
/* 139:    */   }
/* 140:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.ClaveAccesoPendientePublicar
 * JD-Core Version:    0.7.0.1
 */