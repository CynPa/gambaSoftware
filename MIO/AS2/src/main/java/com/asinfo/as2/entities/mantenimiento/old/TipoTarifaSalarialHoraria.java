/*   1:    */ package com.asinfo.as2.entities.mantenimiento.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.TableGenerator;
/*  12:    */ import javax.validation.constraints.Max;
/*  13:    */ import javax.validation.constraints.Min;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="tipo_tarifa_salarial_horaria", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  19:    */ public class TipoTarifaSalarialHoraria
/*  20:    */   extends EntidadBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 3264776768329833338L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="tipo_tarifa_salarial_horaria", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_tarifa_salarial_horaria")
/*  26:    */   @Column(name="id_tipo_tarifa_salarial_horaria")
/*  27:    */   private int idTipoTarifaSalarialHoraria;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   private int idOrganizacion;
/*  30:    */   @Column(name="id_sucursal", nullable=false)
/*  31:    */   private int idSucursal;
/*  32:    */   @Column(name="codigo", nullable=false, length=10)
/*  33:    */   @NotNull
/*  34:    */   @Size(min=2, max=10)
/*  35:    */   private String codigo;
/*  36:    */   @Column(name="nombre", nullable=false, length=50)
/*  37:    */   @NotNull
/*  38:    */   @Size(min=2, max=50)
/*  39:    */   private String nombre;
/*  40:    */   @Column(name="descripcion", length=200)
/*  41:    */   @Size(max=200)
/*  42:    */   private String descripcion;
/*  43:    */   @Column(name="activo", nullable=false)
/*  44:    */   private boolean activo;
/*  45:    */   @Column(name="predeterminado", nullable=false)
/*  46:    */   private boolean predeterminado;
/*  47:    */   @Column(name="porcentaje_recargo_costo", nullable=false, precision=5, scale=2)
/*  48:    */   @Min(0L)
/*  49:    */   @Max(100L)
/*  50:    */   private BigDecimal porcentajeRecargoCosto;
/*  51:    */   @Column(name="porcentaje_recargo_precio", nullable=false, precision=5, scale=2)
/*  52:    */   @Min(0L)
/*  53:    */   @Max(100L)
/*  54:    */   private BigDecimal porcentajeRecargoPrecio;
/*  55:    */   
/*  56:    */   public TipoTarifaSalarialHoraria() {}
/*  57:    */   
/*  58:    */   public TipoTarifaSalarialHoraria(int idTipoTarifaSalarialHoraria, String nombre)
/*  59:    */   {
/*  60: 96 */     this.idTipoTarifaSalarialHoraria = idTipoTarifaSalarialHoraria;
/*  61: 97 */     this.nombre = nombre;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdTipoTarifaSalarialHoraria()
/*  65:    */   {
/*  66:106 */     return this.idTipoTarifaSalarialHoraria;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdTipoTarifaSalarialHoraria(int idTipoTarifaSalarialHoraria)
/*  70:    */   {
/*  71:116 */     this.idTipoTarifaSalarialHoraria = idTipoTarifaSalarialHoraria;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdOrganizacion()
/*  75:    */   {
/*  76:125 */     return this.idOrganizacion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdOrganizacion(int idOrganizacion)
/*  80:    */   {
/*  81:135 */     this.idOrganizacion = idOrganizacion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getIdSucursal()
/*  85:    */   {
/*  86:144 */     return this.idSucursal;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setIdSucursal(int idSucursal)
/*  90:    */   {
/*  91:154 */     this.idSucursal = idSucursal;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getNombre()
/*  95:    */   {
/*  96:163 */     return this.nombre;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setNombre(String nombre)
/* 100:    */   {
/* 101:173 */     this.nombre = nombre;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getDescripcion()
/* 105:    */   {
/* 106:182 */     return this.descripcion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setDescripcion(String descripcion)
/* 110:    */   {
/* 111:192 */     this.descripcion = descripcion;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean isActivo()
/* 115:    */   {
/* 116:201 */     return this.activo;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setActivo(boolean activo)
/* 120:    */   {
/* 121:211 */     this.activo = activo;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean isPredeterminado()
/* 125:    */   {
/* 126:220 */     return this.predeterminado;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setPredeterminado(boolean predeterminado)
/* 130:    */   {
/* 131:230 */     this.predeterminado = predeterminado;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String getCodigo()
/* 135:    */   {
/* 136:239 */     return this.codigo;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setCodigo(String codigo)
/* 140:    */   {
/* 141:249 */     this.codigo = codigo;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public BigDecimal getPorcentajeRecargoCosto()
/* 145:    */   {
/* 146:258 */     return this.porcentajeRecargoCosto;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setPorcentajeRecargoCosto(BigDecimal porcentajeRecargoCosto)
/* 150:    */   {
/* 151:268 */     this.porcentajeRecargoCosto = porcentajeRecargoCosto;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public BigDecimal getPorcentajeRecargoPrecio()
/* 155:    */   {
/* 156:277 */     return this.porcentajeRecargoPrecio;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setPorcentajeRecargoPrecio(BigDecimal porcentajeRecargoPrecio)
/* 160:    */   {
/* 161:287 */     this.porcentajeRecargoPrecio = porcentajeRecargoPrecio;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public int getId()
/* 165:    */   {
/* 166:292 */     return this.idTipoTarifaSalarialHoraria;
/* 167:    */   }
/* 168:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.old.TipoTarifaSalarialHoraria
 * JD-Core Version:    0.7.0.1
 */