/*  1:   */ package com.asinfo.as2.entities.mantenimiento;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.EntidadBase;
/*  4:   */ import com.asinfo.as2.entities.PersonaResponsable;
/*  5:   */ import java.io.Serializable;
/*  6:   */ import javax.persistence.Column;
/*  7:   */ import javax.persistence.Entity;
/*  8:   */ import javax.persistence.FetchType;
/*  9:   */ import javax.persistence.GeneratedValue;
/* 10:   */ import javax.persistence.GenerationType;
/* 11:   */ import javax.persistence.Id;
/* 12:   */ import javax.persistence.JoinColumn;
/* 13:   */ import javax.persistence.ManyToOne;
/* 14:   */ import javax.persistence.Table;
/* 15:   */ import javax.persistence.TableGenerator;
/* 16:   */ import javax.validation.constraints.NotNull;
/* 17:   */ 
/* 18:   */ @Entity
/* 19:   */ @Table(name="responsable_orden_trabajo_mantenimiento", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_orden_trabajo_mantenimiento", "id_persona_responsable"})})
/* 20:   */ public class ResponsableOrdenTrabajoMantenimiento
/* 21:   */   extends EntidadBase
/* 22:   */   implements Serializable
/* 23:   */ {
/* 24:   */   private static final long serialVersionUID = 1L;
/* 25:   */   @Id
/* 26:   */   @TableGenerator(name="responsable_orden_trabajo_mantenimiento", initialValue=0, allocationSize=50)
/* 27:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="responsable_orden_trabajo_mantenimiento")
/* 28:   */   @Column(name="id_responsable_orden_trabajo_mantenimiento")
/* 29:   */   private int idResponsableOrdenTrabajoMantenimiento;
/* 30:   */   @Column(name="id_sucursal", nullable=false)
/* 31:   */   @NotNull
/* 32:   */   private int idSucursal;
/* 33:   */   @Column(name="id_organizacion", nullable=false)
/* 34:   */   @NotNull
/* 35:   */   private int idOrganizacion;
/* 36:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 37:   */   @JoinColumn(name="id_orden_trabajo_mantenimiento", nullable=false)
/* 38:   */   @NotNull
/* 39:   */   private OrdenTrabajoMantenimiento ordenTrabajoMantenimiento;
/* 40:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 41:   */   @JoinColumn(name="id_persona_responsable", nullable=false)
/* 42:   */   @NotNull
/* 43:   */   private PersonaResponsable personaResponsable;
/* 44:   */   
/* 45:   */   public int getId()
/* 46:   */   {
/* 47:56 */     return this.idResponsableOrdenTrabajoMantenimiento;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public int getIdResponsableOrdenTrabajoMantenimiento()
/* 51:   */   {
/* 52:60 */     return this.idResponsableOrdenTrabajoMantenimiento;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void setIdResponsableOrdenTrabajoMantenimiento(int idResponsableOrdenTrabajoMantenimiento)
/* 56:   */   {
/* 57:64 */     this.idResponsableOrdenTrabajoMantenimiento = idResponsableOrdenTrabajoMantenimiento;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public int getIdSucursal()
/* 61:   */   {
/* 62:68 */     return this.idSucursal;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void setIdSucursal(int idSucursal)
/* 66:   */   {
/* 67:72 */     this.idSucursal = idSucursal;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public int getIdOrganizacion()
/* 71:   */   {
/* 72:76 */     return this.idOrganizacion;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public void setIdOrganizacion(int idOrganizacion)
/* 76:   */   {
/* 77:80 */     this.idOrganizacion = idOrganizacion;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public OrdenTrabajoMantenimiento getOrdenTrabajoMantenimiento()
/* 81:   */   {
/* 82:84 */     return this.ordenTrabajoMantenimiento;
/* 83:   */   }
/* 84:   */   
/* 85:   */   public void setOrdenTrabajoMantenimiento(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 86:   */   {
/* 87:88 */     this.ordenTrabajoMantenimiento = ordenTrabajoMantenimiento;
/* 88:   */   }
/* 89:   */   
/* 90:   */   public PersonaResponsable getPersonaResponsable()
/* 91:   */   {
/* 92:92 */     return this.personaResponsable;
/* 93:   */   }
/* 94:   */   
/* 95:   */   public void setPersonaResponsable(PersonaResponsable personaResponsable)
/* 96:   */   {
/* 97:96 */     this.personaResponsable = personaResponsable;
/* 98:   */   }
/* 99:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.ResponsableOrdenTrabajoMantenimiento
 * JD-Core Version:    0.7.0.1
 */