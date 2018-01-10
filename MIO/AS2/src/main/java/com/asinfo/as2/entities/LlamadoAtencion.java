/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.persistence.Temporal;
/*  16:    */ import javax.persistence.TemporalType;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ import org.hibernate.annotations.ColumnDefault;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="llamado_atencion")
/*  23:    */ public class LlamadoAtencion
/*  24:    */   extends EntidadBase
/*  25:    */   implements Serializable
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = -1133788217704360695L;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   private int idOrganizacion;
/*  30:    */   @Column(name="id_sucursal", nullable=false)
/*  31:    */   private int idSucursal;
/*  32:    */   @Id
/*  33:    */   @TableGenerator(name="llamado_atencion", initialValue=0, allocationSize=50)
/*  34:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="llamado_atencion")
/*  35:    */   @Column(name="id_llamado_atencion", unique=true, nullable=false)
/*  36:    */   private int idLlamadoAtencion;
/*  37:    */   @Column(name="descripcion", nullable=true, length=200)
/*  38:    */   @Size(max=200)
/*  39:    */   private String descripcion;
/*  40:    */   @Column(name="activo", nullable=false)
/*  41:    */   private boolean activo;
/*  42:    */   @Column(name="fecha_desde", nullable=false)
/*  43:    */   @Temporal(TemporalType.DATE)
/*  44:    */   private Date fechaDesde;
/*  45:    */   @Column(name="fichero", nullable=true, length=200)
/*  46:    */   @Size(max=200)
/*  47:    */   private String fichero;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_motivo_llamado_atencion", nullable=false, insertable=true, updatable=true)
/*  50:    */   @NotNull
/*  51:    */   private MotivoLlamadoAtencion motivoLlamadoAtencion;
/*  52:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_empleado", nullable=false, insertable=true, updatable=false)
/*  54:    */   @NotNull
/*  55:    */   private Empleado empleado;
/*  56:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  57:    */   @JoinColumn(name="id_documento", nullable=true)
/*  58:    */   private Documento documento;
/*  59:    */   @ColumnDefault("''")
/*  60:    */   @Column(name="numero", nullable=false, length=20)
/*  61:    */   @NotNull
/*  62:    */   @Size(max=20)
/*  63:    */   private String numero;
/*  64:    */   
/*  65:    */   public int getId()
/*  66:    */   {
/*  67: 84 */     return this.idLlamadoAtencion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdLlamadoAtencion()
/*  71:    */   {
/*  72: 88 */     return this.idLlamadoAtencion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdLlamadoAtencion(int idLlamadoAtencion)
/*  76:    */   {
/*  77: 92 */     this.idLlamadoAtencion = idLlamadoAtencion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdOrganizacion()
/*  81:    */   {
/*  82: 96 */     return this.idOrganizacion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdOrganizacion(int idOrganizacion)
/*  86:    */   {
/*  87:100 */     this.idOrganizacion = idOrganizacion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getIdSucursal()
/*  91:    */   {
/*  92:104 */     return this.idSucursal;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setIdSucursal(int idSucursal)
/*  96:    */   {
/*  97:108 */     this.idSucursal = idSucursal;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getDescripcion()
/* 101:    */   {
/* 102:112 */     return this.descripcion;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setDescripcion(String descripcion)
/* 106:    */   {
/* 107:116 */     this.descripcion = descripcion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean isActivo()
/* 111:    */   {
/* 112:120 */     return this.activo;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setActivo(boolean activo)
/* 116:    */   {
/* 117:124 */     this.activo = activo;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public MotivoLlamadoAtencion getMotivoLlamadoAtencion()
/* 121:    */   {
/* 122:128 */     return this.motivoLlamadoAtencion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setMotivoLlamadoAtencion(MotivoLlamadoAtencion motivoLlamadoAtencion)
/* 126:    */   {
/* 127:132 */     this.motivoLlamadoAtencion = motivoLlamadoAtencion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Date getFechaDesde()
/* 131:    */   {
/* 132:136 */     return this.fechaDesde;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setFechaDesde(Date fechaDesde)
/* 136:    */   {
/* 137:140 */     this.fechaDesde = fechaDesde;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String getFichero()
/* 141:    */   {
/* 142:144 */     return this.fichero;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setFichero(String fichero)
/* 146:    */   {
/* 147:148 */     this.fichero = fichero;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Empleado getEmpleado()
/* 151:    */   {
/* 152:152 */     return this.empleado;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setEmpleado(Empleado empleado)
/* 156:    */   {
/* 157:156 */     this.empleado = empleado;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Documento getDocumento()
/* 161:    */   {
/* 162:160 */     return this.documento;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setDocumento(Documento documento)
/* 166:    */   {
/* 167:164 */     this.documento = documento;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String getNumero()
/* 171:    */   {
/* 172:168 */     return this.numero;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setNumero(String numero)
/* 176:    */   {
/* 177:172 */     this.numero = numero;
/* 178:    */   }
/* 179:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.LlamadoAtencion
 * JD-Core Version:    0.7.0.1
 */