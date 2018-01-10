/*   1:    */ package com.asinfo.as2.entities.mantenimiento.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.enumeraciones.EstadoOrdenServicioMantenimiento;
/*   5:    */ import java.util.Date;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.persistence.Temporal;
/*  17:    */ import javax.persistence.TemporalType;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ import javax.validation.constraints.Size;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="orden_servicio_mantenimiento", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero"})})
/*  23:    */ public class OrdenServicioMantenimiento
/*  24:    */   extends EntidadBase
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -4001136650819787274L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="orden_servicio_mantenimiento", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="orden_servicio_mantenimiento")
/*  30:    */   @Column(name="id_orden_servicio_mantenimiento")
/*  31:    */   private int idOrdenServicioMantenimiento;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   private int idSucursal;
/*  36:    */   @Temporal(TemporalType.DATE)
/*  37:    */   @Column(name="fecha", nullable=false)
/*  38:    */   @NotNull
/*  39:    */   private Date fecha;
/*  40:    */   @Column(name="numero", nullable=false, length=20)
/*  41:    */   @NotNull
/*  42:    */   @Size(min=2, max=20)
/*  43:    */   private String numero;
/*  44:    */   @Column(name="descripcion", length=200)
/*  45:    */   @Size(max=200)
/*  46:    */   private String descripcion;
/*  47:    */   @Column(name="estado", nullable=false)
/*  48:    */   private EstadoOrdenServicioMantenimiento estadoOrdenServicioMantenimiento;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_procedimiento", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private Procedimiento procedimiento;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_articulo_servicio", nullable=false)
/*  55:    */   @NotNull
/*  56:    */   private ArticuloServicio articuloServicio;
/*  57:    */   
/*  58:    */   public OrdenServicioMantenimiento() {}
/*  59:    */   
/*  60:    */   public OrdenServicioMantenimiento(int idOrdenServicioMantenimiento, String numero)
/*  61:    */   {
/*  62:116 */     this.idOrdenServicioMantenimiento = idOrdenServicioMantenimiento;
/*  63:117 */     this.numero = numero;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdOrdenServicioMantenimiento()
/*  67:    */   {
/*  68:130 */     return this.idOrdenServicioMantenimiento;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdOrdenServicioMantenimiento(int idOrdenServicioMantenimiento)
/*  72:    */   {
/*  73:140 */     this.idOrdenServicioMantenimiento = idOrdenServicioMantenimiento;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdOrganizacion()
/*  77:    */   {
/*  78:149 */     return this.idOrganizacion;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdOrganizacion(int idOrganizacion)
/*  82:    */   {
/*  83:159 */     this.idOrganizacion = idOrganizacion;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getIdSucursal()
/*  87:    */   {
/*  88:168 */     return this.idSucursal;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIdSucursal(int idSucursal)
/*  92:    */   {
/*  93:178 */     this.idSucursal = idSucursal;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getNumero()
/*  97:    */   {
/*  98:187 */     return this.numero;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setNumero(String numero)
/* 102:    */   {
/* 103:197 */     this.numero = numero;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public EstadoOrdenServicioMantenimiento getEstadoOrdenServicioMantenimiento()
/* 107:    */   {
/* 108:206 */     return this.estadoOrdenServicioMantenimiento;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setEstadoOrdenServicioMantenimiento(EstadoOrdenServicioMantenimiento estadoOrdenServicioMantenimiento)
/* 112:    */   {
/* 113:216 */     this.estadoOrdenServicioMantenimiento = estadoOrdenServicioMantenimiento;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Procedimiento getProcedimiento()
/* 117:    */   {
/* 118:225 */     return this.procedimiento;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setProcedimiento(Procedimiento procedimiento)
/* 122:    */   {
/* 123:235 */     this.procedimiento = procedimiento;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String getDescripcion()
/* 127:    */   {
/* 128:244 */     return this.descripcion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setDescripcion(String descripcion)
/* 132:    */   {
/* 133:254 */     this.descripcion = descripcion;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public ArticuloServicio getArticuloServicio()
/* 137:    */   {
/* 138:263 */     return this.articuloServicio;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setArticuloServicio(ArticuloServicio articuloServicio)
/* 142:    */   {
/* 143:273 */     this.articuloServicio = articuloServicio;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Date getFecha()
/* 147:    */   {
/* 148:282 */     return this.fecha;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setFecha(Date fecha)
/* 152:    */   {
/* 153:292 */     this.fecha = fecha;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public int getId()
/* 157:    */   {
/* 158:297 */     return this.idOrdenServicioMantenimiento;
/* 159:    */   }
/* 160:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.old.OrdenServicioMantenimiento
 * JD-Core Version:    0.7.0.1
 */