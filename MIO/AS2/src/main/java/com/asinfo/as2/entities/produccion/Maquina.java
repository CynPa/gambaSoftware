/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CentroTrabajo;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.DecimalMin;
/*  18:    */ import javax.validation.constraints.Digits;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="maquina", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  24:    */ public class Maquina
/*  25:    */   extends EntidadBase
/*  26:    */   implements Serializable
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -6740736184245829234L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="maquina", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="maquina")
/*  32:    */   @Column(name="id_maquina")
/*  33:    */   private int idMaquina;
/*  34:    */   @Column(name="id_organizacion")
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal")
/*  37:    */   private int idSucursal;
/*  38:    */   @Column(name="codigo", nullable=false, length=20)
/*  39:    */   @NotNull
/*  40:    */   @Size(min=1, max=20)
/*  41:    */   private String codigo;
/*  42:    */   @Column(name="nombre", nullable=false, length=100)
/*  43:    */   @NotNull
/*  44:    */   @Size(min=2, max=100)
/*  45:    */   private String nombre;
/*  46:    */   @Column(name="descripcion", length=200, nullable=true)
/*  47:    */   @Size(max=200)
/*  48:    */   private String descripcion;
/*  49:    */   @Column(name="predeterminado", nullable=false)
/*  50:    */   private boolean predeterminado;
/*  51:    */   @Column(name="activo", nullable=false)
/*  52:    */   private boolean activo;
/*  53:    */   @Column(name="tarifa", precision=12, scale=4, nullable=false)
/*  54:    */   @Digits(integer=12, fraction=4)
/*  55:    */   @DecimalMin("0.0000")
/*  56: 82 */   private BigDecimal tarifa = BigDecimal.ZERO;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_departamento", nullable=true)
/*  59:    */   private CentroTrabajo centroTrabajo;
/*  60:    */   
/*  61:    */   public Maquina() {}
/*  62:    */   
/*  63:    */   public Maquina(int idMaquina, String nombre)
/*  64:    */   {
/*  65:104 */     this.idMaquina = idMaquina;
/*  66:105 */     this.nombre = nombre;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getIdMaquina()
/*  70:    */   {
/*  71:115 */     return this.idMaquina;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdMaquina(int idMaquina)
/*  75:    */   {
/*  76:125 */     this.idMaquina = idMaquina;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int getIdOrganizacion()
/*  80:    */   {
/*  81:134 */     return this.idOrganizacion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setIdOrganizacion(int idOrganizacion)
/*  85:    */   {
/*  86:144 */     this.idOrganizacion = idOrganizacion;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getIdSucursal()
/*  90:    */   {
/*  91:153 */     return this.idSucursal;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdSucursal(int idSucursal)
/*  95:    */   {
/*  96:163 */     this.idSucursal = idSucursal;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getCodigo()
/* 100:    */   {
/* 101:172 */     return this.codigo;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setCodigo(String codigo)
/* 105:    */   {
/* 106:182 */     this.codigo = codigo;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String getNombre()
/* 110:    */   {
/* 111:191 */     return this.nombre;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setNombre(String nombre)
/* 115:    */   {
/* 116:201 */     this.nombre = nombre;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getDescripcion()
/* 120:    */   {
/* 121:210 */     return this.descripcion;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setDescripcion(String descripcion)
/* 125:    */   {
/* 126:220 */     this.descripcion = descripcion;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean isPredeterminado()
/* 130:    */   {
/* 131:229 */     return this.predeterminado;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setPredeterminado(boolean predeterminado)
/* 135:    */   {
/* 136:239 */     this.predeterminado = predeterminado;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean isActivo()
/* 140:    */   {
/* 141:248 */     return this.activo;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setActivo(boolean activo)
/* 145:    */   {
/* 146:258 */     this.activo = activo;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public CentroTrabajo getCentroTrabajo()
/* 150:    */   {
/* 151:267 */     return this.centroTrabajo;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setCentroTrabajo(CentroTrabajo centroTrabajo)
/* 155:    */   {
/* 156:277 */     this.centroTrabajo = centroTrabajo;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public BigDecimal getTarifa()
/* 160:    */   {
/* 161:286 */     return this.tarifa;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setTarifa(BigDecimal tarifa)
/* 165:    */   {
/* 166:296 */     this.tarifa = tarifa;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public int getId()
/* 170:    */   {
/* 171:306 */     return this.idMaquina;
/* 172:    */   }
/* 173:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.Maquina
 * JD-Core Version:    0.7.0.1
 */